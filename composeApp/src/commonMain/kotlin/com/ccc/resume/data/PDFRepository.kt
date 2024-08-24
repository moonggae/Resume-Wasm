package com.ccc.resume.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class PDFRepository(
    private val client: HttpClient
) {
    suspend fun ocrPDFFile(file: ByteArray, languages: List<String>): ByteArray {
        return client.post("${Ktor.host}/ocr") {
            setBody(MultiPartFormDataContent(
                formData {
                    languages.forEach {
                        append("lang", it)
                    }
                    append("file", file, Headers.build {
                        append(HttpHeaders.ContentType, "application/pdf")
                        append(HttpHeaders.ContentDisposition, "filename=\"input.pdf\"")
                    })
                }
            ))
        }.body<ByteArray>()
    }
}