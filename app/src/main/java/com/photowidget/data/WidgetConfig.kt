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

enum class WidgetClickAction {
    DECORATIVE,
    OPEN_APP,
    OPEN_WIDGET_SETTINGS,
}

data class WidgetConfig(
    val widgetNumber: Int = 0,
    val displayName: String? = null,
    val imageUri: String? = null,
    val rotationDegrees: Int = 0,
    val imageAlignment: ImageAlignment = ImageAlignment.CENTER,
    val scaleMode: ScaleMode = ScaleMode.COVER,
    val shape: WidgetShape = WidgetShape.ROUNDED_RECT,
    val cornerRadiusDp: Int = 16,
    val clickAction: WidgetClickAction = WidgetClickAction.OPEN_WIDGET_SETTINGS,
)
