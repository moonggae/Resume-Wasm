package com.ccc.resume

sealed class PDFDownloadUiState {
    data object Idle : PDFDownloadUiState()
    data object Loading : PDFDownloadUiState()
    data class Fail(val throwable: Throwable) : PDFDownloadUiState()
}