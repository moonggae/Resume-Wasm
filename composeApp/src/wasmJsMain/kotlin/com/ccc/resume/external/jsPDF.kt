package com.ccc.resume.external

import org.khronos.webgl.ArrayBuffer

@JsModule("jspdf")
external class jsPDF(
    orientation: String = definedExternally,
    unit: String = definedExternally,
    format: String = definedExternally
) {
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

    fun addPage(
        format: String = definedExternally
    )

    fun output(type: String): JsAny?
}

fun jsPDF.getArrayBuffer(): ArrayBuffer = output("arraybuffer") as ArrayBuffer