package com.ccc.resume

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.resume.components.KeyValueList
import com.ccc.resume.designsystem.getTextStyleFromString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class DataUiState {
    data object Loading: DataUiState()
    data class Fail(val exception: Throwable): DataUiState()

    @Serializable
    data class Success(
        val title: String,
        val github: String,
        val pages:List<Page>
    ): DataUiState()
}

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
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colors.surface)
                    .padding(80.dp),
            ) {
                contents.forEach { content ->
                    content.composable()
                }
            }
        }
}

@Serializable
sealed class Content {
    abstract val type: String
    abstract val composable: @Composable () -> Unit
}

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
                Text(
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

@Serializable
@SerialName("key_value_list")
data class KeyValueListContent(
    override val type: String = "key_value_list",
    val value: List<KeyValueItem>,

    @SerialName("vertical_padding")
    val verticalPadding: Int = 8,

    @SerialName("horizontal_padding")
    val horizontalPadding: Int = 32,

    @SerialName("key_style")
    val keyStyle: String = "subtitle1",

    @SerialName("value_style")
    val valueStyle: String = "body2",

) : Content() {
    override val composable: @Composable () -> Unit
        get() = {
            KeyValueList(
                data = value.map { it.key to it.value },
                verticalPaddingValues = verticalPadding.dp,
                horizontalPaddingValues = horizontalPadding.dp,
                keyStyle = getTextStyleFromString(keyStyle) ?: MaterialTheme.typography.subtitle1,
                valueStyle = getTextStyleFromString(valueStyle) ?: MaterialTheme.typography.body2,
            )
        }
}

@Serializable
data class KeyValueItem(
    val key: String,
    val value: String
)
