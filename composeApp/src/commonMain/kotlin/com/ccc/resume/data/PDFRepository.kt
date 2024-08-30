package com.ccc.resume.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class PDFRepository(
    private val client: HttpClient,
    private val host: String
) {
    suspend fun ocrPDFFile(file: ByteArray, languages: List<String>): Result<ByteArray> {
        return runCatching<ByteArray> {
            val response = client.post("$host/ocr") {
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
            }

            if (response.status != HttpStatusCode.OK) {
                val exception = response.body<String>()
                throw Exception(exception)
            } else {
                response.body<ByteArray>()
            }
        }
    }
}