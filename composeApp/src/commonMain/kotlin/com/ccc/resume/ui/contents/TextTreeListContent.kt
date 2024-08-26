package com.ccc.resume.ui.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.resume.ui.components.ResumeText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("text_tree_list")
data class TextTreeListContent(
    override val type: String = "tree_list",

    @SerialName("vertical_paddings")
    val verticalPaddings: List<Int> = listOf(8, 4, 4),

    val values: List<TextTreeItem>
) : Content() {
    @Serializable
    data class TextTreeItem(
        val text: String,
        val children: List<TextTreeItem>? = null
    )

    override val composable: @Composable () -> Unit
        get() = {
            TextTreeList(values, verticalPaddings)
        }
}

@Composable
fun TextTreeList(
    items: List<TextTreeListContent.TextTreeItem>,
    verticalPaddings: List<Int>,
    level: Int = 0
) {
    val verticalPadding = verticalPaddings.getOrElse(level) { 0 }

    Column(verticalArrangement = Arrangement.spacedBy(verticalPadding.dp)) {
        items.forEach { item ->
            Row(modifier = Modifier.padding(start = (level * 16).dp)) {
                ResumeText("•  ${item.text}")
            }
            item.children?.let { children ->
                TextTreeList(
                    items = children,
                    verticalPaddings = verticalPaddings,
                    level = level + 1
                )
            }
        }
    }
}