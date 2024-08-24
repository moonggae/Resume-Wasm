package com.ccc.resume.ui.contents

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ccc.resume.designsystem.getTextStyleFromString
import com.ccc.resume.ui.components.KeyValueList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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