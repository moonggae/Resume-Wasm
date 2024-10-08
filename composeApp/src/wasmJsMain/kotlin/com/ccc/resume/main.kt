package com.ccc.resume

import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ccc.resume.data.PDFRepository
import com.ccc.resume.data.getOcrServerUrl
import com.ccc.resume.ui.App
import io.ktor.client.*
import kotlinx.browser.document
import kotlinx.browser.window

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val pdfViewModel: PDFDownloadViewModel = viewModel {
            PDFDownloadViewModel(PDFRepository(HttpClient(), getOcrServerUrl()))
        }

        val downloadState by pdfViewModel.uiState.collectAsStateWithLifecycle()

        App(
            onClickCode = { url ->
                window.open(url)
            },
            onClickDownload = { pages, filename ->
                pdfViewModel.exportComposableToPdf(pages, filename)
            },
            downloadState = downloadState
        )
    }
}