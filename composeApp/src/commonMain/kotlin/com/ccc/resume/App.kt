package com.ccc.resume

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ccc.resume.components.AppBar
import com.ccc.resume.designsystem.ResumeTheme

@Composable
fun App() {
    ResumeTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            AppBar(
                title = "Resume.pdf",
                onClickCode = { onClickCode() },
                onClickDownload = { onClickDownload() }
            )
        }
    }
}

expect fun onClickCode()
expect fun onClickDownload()
