package com.photowidget.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PhotoWidgetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) PhotoWidgetDarkColorScheme else PhotoWidgetLightColorScheme,
        typography = PhotoWidgetTypography,
        shapes = PhotoWidgetShapes,
        content = content,
    )
}
