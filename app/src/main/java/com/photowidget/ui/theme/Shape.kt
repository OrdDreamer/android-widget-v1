package com.photowidget.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Radius scale from "Photo Widget Design System/tokens/effects.css": soft but not pill-happy —
// pill reserved for chips/toggles only.
val RadiusSm = 8.dp
val RadiusMd = 14.dp
val RadiusLg = 20.dp
val RadiusXl = 28.dp

val PhotoWidgetShapes = Shapes(
    extraSmall = RoundedCornerShape(RadiusSm),
    small = RoundedCornerShape(RadiusMd),
    medium = RoundedCornerShape(RadiusLg),
    large = RoundedCornerShape(RadiusXl),
    extraLarge = RoundedCornerShape(percent = 50),
)
