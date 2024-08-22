package com.ccc.resume.designsystem

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

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
                content()
            }
        }
    )
}