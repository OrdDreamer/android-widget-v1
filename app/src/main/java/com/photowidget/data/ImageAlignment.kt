package com.photowidget.data

import androidx.compose.ui.Alignment as ComposeAlignment

enum class ImageAlignment {
    CENTER,
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    ;

    fun toComposeAlignment(): ComposeAlignment = when (this) {
        CENTER -> ComposeAlignment.Center
        TOP -> ComposeAlignment.TopCenter
        BOTTOM -> ComposeAlignment.BottomCenter
        LEFT -> ComposeAlignment.CenterStart
        RIGHT -> ComposeAlignment.CenterEnd
        TOP_LEFT -> ComposeAlignment.TopStart
        TOP_RIGHT -> ComposeAlignment.TopEnd
        BOTTOM_LEFT -> ComposeAlignment.BottomStart
        BOTTOM_RIGHT -> ComposeAlignment.BottomEnd
    }

    fun offsetX(targetWidth: Float, contentWidth: Float): Float = when (this) {
        CENTER, TOP, BOTTOM -> (targetWidth - contentWidth) / 2f
        LEFT, TOP_LEFT, BOTTOM_LEFT -> 0f
        RIGHT, TOP_RIGHT, BOTTOM_RIGHT -> targetWidth - contentWidth
    }

    fun offsetY(targetHeight: Float, contentHeight: Float): Float = when (this) {
        CENTER, LEFT, RIGHT -> (targetHeight - contentHeight) / 2f
        TOP, TOP_LEFT, TOP_RIGHT -> 0f
        BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT -> targetHeight - contentHeight
    }
}
