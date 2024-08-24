package com.ccc.resume.ui.contents

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
sealed class Content {
    abstract val type: String
    abstract val composable: @Composable () -> Unit
}