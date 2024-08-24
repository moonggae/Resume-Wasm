package com.ccc.resume.ui

import com.ccc.resume.ui.contents.Page
import kotlinx.serialization.Serializable

sealed class DataUiState {
    data object Loading: DataUiState()
    data class Fail(val exception: Throwable): DataUiState()

    @Serializable
    data class Success(
        val title: String,
        val github: String,
        val pages:List<Page>
    ): DataUiState()
}
