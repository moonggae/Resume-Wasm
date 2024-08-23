package com.ccc.resume

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ccc.resume.components.AppBar
import com.ccc.resume.designsystem.ResumeTheme

@Composable
fun App(
    viewModel: DataViewModel = viewModel { DataViewModel() },
    onClickCode: (url: String) -> Unit,
    onClickDownload: (pages: List<Page>, filename: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ResumeTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            when (val state = uiState) {
                is DataUiState.Fail -> {
                    Text(
                        state.exception.message?: "Unknown Error",
                        color = MaterialTheme.colors.onBackground
                    )
                }
                is DataUiState.Loading -> {
                    Text(
                        "Loading",
                        color = MaterialTheme.colors.onBackground
                    )
                }
                is DataUiState.Success -> {
                    AppBar(
                        title = state.title,
                        onClickCode = { onClickCode(state.github) },
                        onClickDownload = { onClickDownload(state.pages, state.title) }
                    )

                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        state.pages.forEachIndexed { index, page ->
                            page.composable()
                            if (index != state.pages.lastIndex) {
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
