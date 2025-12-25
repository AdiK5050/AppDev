package com.adi_chat.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
actual fun AppTheme(
    darkTheme: Boolean,
    content: @Composable (() -> Unit)
) {
    val colorScheme =
        if(darkTheme) darkScheme
        else lightScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}