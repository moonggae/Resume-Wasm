package com.ccc.resume.ui.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable

@Serializable
data class Page(
    val contents: List<Content>
) {
    val composable: @Composable () -> Unit
        get() = {
            Column(
                modifier = Modifier
                    .width(1000.dp)
                    .height((1000 * 1.414).dp)
                    .background(MaterialTheme.colors.surface)
                    .padding(80.dp),
            ) {
                contents.forEach { content ->
                    content.composable()
                }
            }
        }
}