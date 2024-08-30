package com.ccc.resume.data

import Resume.composeApp.BuildConfig

fun getEnvOcrServerUrl(): String = js("window.env.OCR_SERVER")
fun getOcrServerUrl(): String {
    return (try {
        getEnvOcrServerUrl()
    } catch (e: Throwable) {
        BuildConfig.OCR_SERVER
    }).run {
        if (startsWith("http")) this
        else if (this.isBlank()) ""
        else "http://$this"
    }
}