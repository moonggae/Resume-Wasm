package com.ccc.resume.external

import org.khronos.webgl.ArrayBuffer

@JsModule("jspdf")
external class jsPDF {
    fun addImage(
        imageData: String,
        format: String,
        x: Float,
        y: Float,
        width: Float? = definedExternally,
        height: Float? = definedExternally,
        alias: String,
        compression: String,
        rotation: Int
    )

    fun save(filename: String)

    fun addPage()

    fun output(type: String): JsAny?
}

fun jsPDF.getArrayBuffer(): ArrayBuffer = output("arraybuffer") as ArrayBuffer