package com.photowidget.ui.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween

// One calm ease-out curve, three durations, no spring()/bounce anywhere — per
// "Photo Widget Design System/guidelines/brand-motion.card.html" and
// guidelines/jetpack-compose.md § Motion.
val CalmEasing: Easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)

const val DurationFast = 150
const val DurationBase = 240
const val DurationSlow = 420

fun <T> calmTween(durationMillis: Int = DurationBase) = tween<T>(durationMillis, easing = CalmEasing)
