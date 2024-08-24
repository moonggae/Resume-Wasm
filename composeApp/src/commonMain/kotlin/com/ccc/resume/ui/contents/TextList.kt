package com.ccc.resume.ui.contents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.resume.designsystem.getTextStyleFromString
import com.ccc.resume.ui.components.ResumeText
import com.ccc.resume.util.conditional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("text_list")
data class TextList(
    override val type: String = "text_list",

    @SerialName("vertical_padding")
    val verticalPadding: Int = 8,

    val style: String = "body1",

    @SerialName("show_dot")
    val showDot: Boolean = true,

    val values: List<String>
) : Content() {
    override val composable: @Composable () -> Unit
        get() = {
            Column {
                values.forEachIndexed { index, text ->
                    ResumeText(
                        text = "${if(showDot) "•  " else ""}$text",
                        style = getTextStyleFromString(style) ?: MaterialTheme.typography.body1,
                        modifier = Modifier.conditional(values.lastIndex != index) {
                            padding(bottom = verticalPadding.dp)
                        }
                    )
                }
            }
        }
}

