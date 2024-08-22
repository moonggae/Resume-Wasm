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
        subtitle2 = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
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


@Composable
fun getTextStyleFromString(string: String): TextStyle? =
    when (string) {
        "h1" -> MaterialTheme.typography.h1
        "h2" -> MaterialTheme.typography.h2
        "h3" -> MaterialTheme.typography.h3
        "h4" -> MaterialTheme.typography.h4
        "h5" -> MaterialTheme.typography.h5
        "h6" -> MaterialTheme.typography.h6
        "subtitle1" -> MaterialTheme.typography.subtitle1
        "subtitle2" -> MaterialTheme.typography.subtitle2
        "body1" -> MaterialTheme.typography.body1
        "body2" -> MaterialTheme.typography.body2
        "button" -> MaterialTheme.typography.button
        "caption" -> MaterialTheme.typography.caption
        "overline" -> MaterialTheme.typography.overline
        else -> null
    }