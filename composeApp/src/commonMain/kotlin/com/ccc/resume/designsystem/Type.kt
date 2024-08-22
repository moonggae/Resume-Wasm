package com.ccc.resume.designsystem

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ResumeTypography(colors: Colors = MaterialTheme.colors): Typography =
    Typography(
        defaultFontFamily = PretendardFont,
        h1 = TextStyle(
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface
        ),
        h4 = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.onSurface
        ),
        h5 = TextStyle(
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.onSurface
        ),
        subtitle1 = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.onSurface
        ),
        body1 = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = colors.onSurface
        ),
        body2 = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            color = colors.onSurface
        )
    )
