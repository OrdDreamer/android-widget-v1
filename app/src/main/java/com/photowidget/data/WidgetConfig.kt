package com.photowidget.data

enum class ScaleMode {
    COVER,
    CONTAIN,
}

enum class WidgetShape {
    RECTANGLE,
    ROUNDED_RECT,
    CIRCLE,
}

data class WidgetConfig(
    val imageUri: String? = null,
    val scaleMode: ScaleMode = ScaleMode.COVER,
    val shape: WidgetShape = WidgetShape.ROUNDED_RECT,
    val cornerRadiusDp: Int = 16,
)
