package com.ccc.resume.ui.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.resume.designsystem.getTextStyleFromString
import com.ccc.resume.ui.components.ResumeText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("title")
data class TitleContent(
    override val type: String = "title",
    val value: String,
    val style: String = "h1",

    @SerialName("show_divider")
    val showDivider: Boolean = false
): Content() {
    override val composable: @Composable () -> Unit
        get() = {
            Column {
                ResumeText(
                    text = value,
                    style = getTextStyleFromString(style) ?: MaterialTheme.typography.h1,
                )

                if (showDivider) {
                    Divider(
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.padding(top = 12.dp, bottom = 20.dp)
                    )
                }
            }
        }
}