package com.ccc.resume

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val scope = rememberCoroutineScope()

        App(
            onClickCode = { url ->
                window.open(url)
            },
            onClickDownload = { pages, filename ->
                scope.launch {
                    exportComposableToPdf(pages.map { page -> page.composable }, filename)
                }
            }
        )
    }
}