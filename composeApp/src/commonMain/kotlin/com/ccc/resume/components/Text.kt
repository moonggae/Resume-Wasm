package com.ccc.resume.components

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.kodein.emoji.compose.m2.TextWithPlatformEmoji

@Composable
public fun TextHyperlink(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    val localContentColor = LocalContentColor.current
    val localContentAlpha = LocalContentAlpha.current
    val overrideColorOrUnspecified = if (color.isSpecified) {
        color
    } else if (style.color.isSpecified) {
        style.color
    } else {
        localContentColor.copy(localContentAlpha)
    }

    Text(
        text = buildAnnotatedString {
            withLink(LinkAnnotation.Url(text)) { append(text) }
        },
        modifier = modifier.drawBehind { // Underline이 글자와 겹쳐 있으면 OCR에서 제대로 인식하지 못함
            val strokeWidthPx = 1.dp.toPx()
            val verticalOffset = size.height + 2.sp.toPx()
            drawLine(
                color = overrideColorOrUnspecified,
                strokeWidth = strokeWidthPx,
                start = Offset(0f, verticalOffset),
                end = Offset(size.width, verticalOffset)
            )
        },
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent,
        onTextLayout = onTextLayout,
        style = style
    )
}

@Composable
public fun TextWithHighlight(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
) {
    TextWithPlatformEmoji(
        text = buildAnnotatedString {
            val parts = text.split("`")
            parts.forEachIndexed { index, part ->
                if (index % 2 == 0) {
                    append(part)
                } else {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                        append(part)
                    }
                }
            }
        },
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        inlineContent = inlineContent,
        onTextLayout = onTextLayout,
        style = style
    )
}