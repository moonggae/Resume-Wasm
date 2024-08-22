package com.ccc.resume.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ccc.resume.designsystem.ResumeIcon

@Composable
fun AppBar(
    title: String,
    onClickDownload: () -> Unit,
    onClickCode: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colors.secondary)
            .padding(horizontal = 36.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2.copy(
                color = MaterialTheme.colors.onSecondary
            )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onClickCode) {
                Icon(
                    imageVector = ResumeIcon.Code,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary
                )
            }

            IconButton(onClick = onClickDownload) {
                Icon(
                    imageVector = ResumeIcon.Download,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSecondary
                )
            }
        }
    }
}