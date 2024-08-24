package com.ccc.resume.data

import io.ktor.client.*

object Ktor {
    val client = HttpClient()
    val host: String = "http://localhost:8081"
}