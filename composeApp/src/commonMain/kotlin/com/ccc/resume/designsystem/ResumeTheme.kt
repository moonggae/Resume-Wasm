package com.ccc.resume.designsystem

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ResumeTheme(
    colors: Colors = ResumeColors,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = colors,
        typography = ResumeTypography(colors),
        content = {
            SelectionContainer {
                Surface(Modifier.fillMaxSize()) {
                    content()
                }
            }
        }
    )
}