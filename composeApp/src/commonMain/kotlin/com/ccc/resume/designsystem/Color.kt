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
        onSurface = Color(0xFF4A5568)
    )