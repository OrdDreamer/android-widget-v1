package com.photowidget.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Vibrant tokens from go-to-market/design/v2 (OKLCH hue ~250–320)

val LightPrimary = Color(0xFF5B3FA6)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = Color(0xFFE4D9F8)
val LightOnPrimaryContainer = Color(0xFF2A1460)
val LightSecondaryContainer = Color(0xFFEDE8F5)
val LightOnSecondaryContainer = Color(0xFF3A2F55)
val LightBackground = Color(0xFFF7F3FC)
val LightSurface = Color(0xFFFCFBFE)
val LightOnSurface = Color(0xFF2A2438)
val LightSurfaceVariant = Color(0xFFF3F0FA)
val LightOnSurfaceVariant = Color(0xFF6F6780)
val LightOutline = Color(0xFFCDC6D8)
val LightOutlineVariant = Color(0xFFE6E1EF)
val LightError = Color(0xFFBA1A1A)
val LightOnError = Color(0xFFFFFFFF)

val DarkPrimary = Color(0xFFC9B0FF)
val DarkOnPrimary = Color(0xFF2A1460)
val DarkPrimaryContainer = Color(0xFF463078)
val DarkOnPrimaryContainer = Color(0xFFE8DEFF)
val DarkSecondaryContainer = Color(0xFF3A3350)
val DarkOnSecondaryContainer = Color(0xFFE6E0F2)
val DarkBackground = Color(0xFF16121F)
val DarkSurface = Color(0xFF1C1728)
val DarkOnSurface = Color(0xFFEAE6F2)
val DarkSurfaceVariant = Color(0xFF2C2738)
val DarkOnSurfaceVariant = Color(0xFFB0A9C0)
val DarkOutline = Color(0xFF6F6780)
val DarkOutlineVariant = Color(0xFF484255)
val DarkError = Color(0xFFFFB4AB)
val DarkOnError = Color(0xFF690005)

val GradientStart = Color(0xFF6B9AFF)
val GradientEnd = Color(0xFFC24DFF)
val GradientTitleStart = Color(0xFF5B3FA6)
val GradientTitleEnd = Color(0xFF9B3DBF)
val HeroGradientMid = Color(0xFF9B4DFF)
val HeroGradientEnd = Color(0xFFE06AFF)

fun brandPrimaryBrush(): Brush = Brush.horizontalGradient(
    colors = listOf(GradientStart, GradientEnd),
)

fun brandTitleBrush(): Brush = Brush.horizontalGradient(
    colors = listOf(GradientTitleStart, GradientTitleEnd),
)

@Composable
fun brandContainerBrush(): Brush {
    if (isSystemInDarkTheme()) {
        val container = MaterialTheme.colorScheme.primaryContainer
        return Brush.linearGradient(colors = listOf(container, container))
    }
    return Brush.linearGradient(
        colors = listOf(Color(0xFFE4D9F8), Color(0xFFE8CFF5)),
    )
}

fun heroCardBrush(): Brush = Brush.linearGradient(
    colors = listOf(GradientStart, HeroGradientMid, HeroGradientEnd),
)

fun screenBackgroundBrush(dark: Boolean): Brush = if (dark) {
    Brush.verticalGradient(
        colors = listOf(Color(0xFF1A1528), Color(0xFF16121F)),
    )
} else {
    Brush.verticalGradient(
        colors = listOf(Color(0xFFF7F3FC), Color(0xFFF0EAF8)),
    )
}
