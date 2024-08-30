package com.ccc.resume

import com.ccc.resume.server.BuildConfig
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    embeddedServer(
        factory = Netty,
        port = BuildConfig.OCR_SERVER_PORT.toInt(),
        host = BuildConfig.OCR_SERVER_HOST,
        module = { module(args) }
    ).start(wait = true)
}

fun Application.module(args: Array<String>) {
    install(CORS) {
        val originArg = args.getOrNull(args.indexOf("-allow") + 1) ?: "all"
        when (originArg.lowercase()) {
            "all" -> { anyHost() }
            "same" -> { allowSameOrigin = true }
            else -> { allowHost(originArg) }
        }
        maxAgeInSeconds = 10
    }

    environment.monitor.subscribe(ApplicationStarted) { application ->
        try {
            args.forEach {
                application.environment.log.info(it)
            }

            if (!checkAndCreateUploadsDirectory()) throw Exception("Can't create directory: Uploads")
            if (!checkProgramInstalled("ocrmypdf")) throw Exception("Can't find ocrmypdf")
            if (!checkProgramInstalled("tesseract")) throw Exception("Can't find tesseract")
        } catch (e: Exception) {
            application.environment.log.error("Fail to init:\n$e")
        }
    }

    routing {
        val staticOptionIndex = args.indexOf("-static")
        val staticFilePath = args.getOrNull(staticOptionIndex + 1)

        if (staticOptionIndex > -1 && !staticFilePath.isNullOrBlank()) {
            staticFiles(
                remotePath = "/",
                dir = File(staticFilePath),
                index = "index.html"
            )
        }

        post("/ocr") {
            val (inputFile, languages) = parseMultiParts(call.receiveMultipart())
            val outputFile = File("uploads/${UUID.randomUUID()}.pdf")

            if (inputFile != null) {
                val exitCode = runOcrMyPdf(inputFile, outputFile, languages)

                if (exitCode == 0 || exitCode == 10) {
                    call.respondFile(outputFile)
                } else {
                    call.application.environment.log.error("Fail to ocr: exitCode: $exitCode")
                    call.respondText("Fail to ocr", status = HttpStatusCode.InternalServerError)
                }
            } else {
                call.respondText("Fail to upload file", status = HttpStatusCode.BadRequest)
            }

            inputFile?.delete()
            outputFile.delete()
        }
    }
}


fun checkProgramInstalled(name: String): Boolean {
    val process = ProcessBuilder("which", name).start()
    process.waitFor()
    return process.exitValue() == 0
}

fun checkAndCreateUploadsDirectory(): Boolean {
    val uploadsDir = File("uploads")
    val isSuccess = uploadsDir.exists() || uploadsDir.mkdirs()
    return isSuccess
}

suspend fun parseMultiParts(multiParts: MultiPartData): Pair<File?, List<String>> {
    var inputFile: File? = null
    val lang = mutableListOf<String>()

    multiParts.forEachPart { part ->
        when (part) {
            is PartData.FileItem -> {
                val fileExtension = File(part.originalFileName ?: "").extension
                inputFile = File("uploads/${UUID.randomUUID()}.$fileExtension").apply {
                    part.streamProvider().use { inputStream ->
                        outputStream().buffered().use { inputStream.copyTo(it) }
                    }
                }
            }

            is PartData.FormItem -> {
                lang.add(part.value)
            }

            else -> {}
        }
        part.dispose()
    }

    return inputFile to lang
}


/**
 * support languages: https://github.com/tesseract-ocr/tesseract/blob/main/doc/tesseract.1.asc#languages
 */
fun runOcrMyPdf(
    inputFile: File,
    outputFile: File,
    language: List<String>
): Int {
    val process = ProcessBuilder(
        "ocrmypdf",
        "-l", language.joinToString("+"),
        inputFile.absolutePath,
        outputFile.absolutePath
    ).start()


//    val stdOutput = process.inputStream.bufferedReader().readText()
//    val stdError = process.errorStream.bufferedReader().readText()
    val exitCode = process.waitFor()

//    logger.info("OCRmyPDF Output:\n$stdOutput")
//    logger.error("OCRmyPDF Errors:\n$stdError")
//    logger.info("OCRmyPDF 종료 코드: $exitCode")

    return exitCode
}