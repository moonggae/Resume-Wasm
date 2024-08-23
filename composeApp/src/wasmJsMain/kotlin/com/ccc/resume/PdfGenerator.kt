// PdfGenerator.kt

package com.ccc.resume

import androidx.compose.runtime.Composable
import androidx.compose.ui.renderComposeScene
import androidx.compose.ui.unit.Density
import com.ccc.resume.designsystem.ResumeTheme
import com.ccc.resume.util.toBlob
import org.jetbrains.skia.Image
import org.w3c.dom.url.URL

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
}

object PdfConstants {
    const val PDF_WIDTH = 210f
    const val PDF_HEIGHT = 297f // A4 height
    const val RENDER_WIDTH = 2000
    const val RENDER_HEIGHT = 2828 // Approximately A4 ratio
    const val DENSITY_SCALE = 2f
}

fun exportComposableToPdf(
    pages: List<@Composable () -> Unit>,
    filename: String
) {
    val images = renderPages(pages)
    generatePDF(images, filename)
}

private fun renderPages(pages: List<@Composable () -> Unit>): List<Image> {
    return pages.map { page ->
        renderComposeScene(
            width = PdfConstants.RENDER_WIDTH,
            height = PdfConstants.RENDER_HEIGHT,
            density = Density(PdfConstants.DENSITY_SCALE)
        ) {
            ResumeTheme {
                page()
            }
        }
    }
}

private fun generatePDF(
    images: List<Image>,
    filename: String
) {
    val pdf = jsPDF()

    images.forEachIndexed { index, image ->
        if (index != 0) {
            pdf.addPage()
        }
        addImageToPdf(pdf, image)
    }

    pdf.save(filename)
}

private fun addImageToPdf(pdf: jsPDF, image: Image) {
    val data = image.encodeToData() ?: throw IllegalStateException("Failed to encode image")
    val blob = data.bytes.toBlob("image/png")
    val imageData = URL.createObjectURL(blob)

    pdf.addImage(
        imageData,
        "PNG",
        0f,
        0f,
        PdfConstants.PDF_WIDTH,
        PdfConstants.PDF_HEIGHT,
        "",
        "MEDIUM",
        0
    )
}