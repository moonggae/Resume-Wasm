package com.ccc.resume.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ccc.resume.util.conditional
import com.ccc.resume.util.isValidUri

@Composable
fun KeyValueList(
    data: List<Pair<String, String>>,
    horizontalPaddingValues: Dp = 0.dp,
    verticalPaddingValues: Dp = 0.dp,
    keyStyle: TextStyle = MaterialTheme.typography.subtitle1,
    valueStyle: TextStyle = MaterialTheme.typography.body2,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        TableColumn(
            items = data.map { it.first },
            style = keyStyle,
            verticalPaddingValues = verticalPaddingValues
        )

        Spacer(Modifier.width(horizontalPaddingValues))

        TableColumn(
            items = data.map { it.second },
            style = valueStyle,
            verticalPaddingValues = verticalPaddingValues
        )
    }
}

@Composable
private fun TableColumn(
    items: List<String>,
    style: TextStyle,
    verticalPaddingValues: Dp
) {
    Column {
        items.forEachIndexed { index, item ->
            if (isValidUri(item)) {
                TextHyperlink(
                    text = item,
                    style = style,
                    modifier = Modifier.conditional(index != items.lastIndex) {
                        padding(bottom = verticalPaddingValues)
                    }
                )
            } else {
                TextWithHighlight(
                    text = item,
                    style = style,
                    modifier = Modifier.conditional(index != items.lastIndex) {
                        padding(bottom = verticalPaddingValues)
                    }
                )
            }
        }
    }
}
