package com.ccc.resume.designsystem

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ResumeColors: Colors
    @Composable
    get() = MaterialTheme.colors.copy(
        onPrimary = Color(0xFF33DB84),
        surface = Color(0xFFEDF2F7),
        onSurface = Color(0xFF4A5568),
        background = Color(0xFF525659),
        onBackground = Color(0xFFf1f1f1),
        secondary = Color(0xFF323639),
        onSecondary = Color(0xFFf1f1f1)
    )