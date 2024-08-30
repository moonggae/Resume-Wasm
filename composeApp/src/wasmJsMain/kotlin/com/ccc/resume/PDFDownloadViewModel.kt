package com.ccc.resume

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.renderComposeScene
import androidx.compose.ui.unit.Density
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.resume.data.PDFRepository
import com.ccc.resume.designsystem.ResumeTheme
import com.ccc.resume.external.getArrayBuffer
import com.ccc.resume.external.jsPDF
import com.ccc.resume.ui.PDFDownloadUiState
import com.ccc.resume.ui.contents.Page
import com.ccc.resume.util.toBlob
import com.ccc.resume.util.toByteArray
import kotlinx.browser.document
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.skia.Image
import org.khronos.webgl.Uint8Array
import org.kodein.emoji.compose.EmojiService
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL
import org.w3c.files.Blob

class PDFDownloadViewModel(
    private val repository: PDFRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<PDFDownloadUiState> = MutableStateFlow(PDFDownloadUiState.Idle)
    val uiState: StateFlow<PDFDownloadUiState> = _uiState

    fun exportComposableToPdf(
        pages: List<Page>,
        filename: String
    ) {
        viewModelScope.launch {
            _uiState.value = PDFDownloadUiState.Loading
            runCatching {
                val images = renderPages(pages.map { page -> page.composable })
                val originPDF = generatePDF(images)
                repository.ocrPDFFile(originPDF, listOf("eng", "kor"))
                    .onSuccess { byteArray ->
                        downloadFile(byteArray.toBlob("application/pdf"), filename)
                    }.onFailure {
                        downloadFile(originPDF.toBlob("application/pdf"), filename)
                    }
            }.onSuccess {
                _uiState.value = PDFDownloadUiState.Idle
            }.onFailure {
                _uiState.value = PDFDownloadUiState.Fail(it)
            }
        }
    }

    private fun renderPages(pages: List<@Composable () -> Unit>): List<Image> =
        pages.map { page ->
            renderComposeScene(
                width = RENDER_WIDTH,
                height = RENDER_HEIGHT,
                density = Density(DENSITY_SCALE)
            ) {
                remember { EmojiService.initialize() }
                ResumeTheme {
                    page()
                }
            }
        }


    private fun generatePDF(images: List<Image>): ByteArray {
        val pdf = jsPDF(format = "a4")

        images.forEachIndexed { index, image ->
            if (index != 0) {
                pdf.addPage("a4")
            }
            addImageToPdf(pdf, image)
        }

        val originPdfArrayBuffer = pdf.getArrayBuffer()
        return Uint8Array(originPdfArrayBuffer).toByteArray()
    }

    private fun downloadFile(blob: Blob, filename: String) {
        val a = document.createElement("a") as HTMLAnchorElement

        a.download = filename
        a.target = "_blank"
        a.rel = "noopener"
        a.href = URL.createObjectURL(blob)

        a.click()
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
            PDF_WIDTH,
            PDF_HEIGHT,
            "",
            "Fast",
            0
        )
    }

    companion object PdfConstants {
        const val PDF_WIDTH = 210f
        const val PDF_HEIGHT = 297f
//        const val RENDER_WIDTH = 2000
//        const val RENDER_HEIGHT = 2828 // Approximately A4 ratio
        const val RENDER_WIDTH = 1800
        const val RENDER_HEIGHT = (1800 * 1.414).toInt()
        const val DENSITY_SCALE = 2f
    }
}