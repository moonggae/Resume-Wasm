package com.ccc.resume.ui.contents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("p")
data class PaddingContent(
    override val type: String = "p",
    val value: Int
) : Content() {
    override val composable: @Composable () -> Unit
        get() = {
            Spacer(Modifier.height(value.dp))
        }
}