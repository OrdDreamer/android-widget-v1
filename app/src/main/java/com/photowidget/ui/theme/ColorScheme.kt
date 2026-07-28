package com.photowidget.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Custom M3 ColorScheme filled with Photo Widget's brand tokens instead of Material's default
// purple/teal baseline, following the role table in
// "Photo Widget Design System/guidelines/jetpack-compose.md". Dark values are derived from the
// same inline dark palette documented in Color.kt (no purple/teal M3 defaults leak through
// since every role below is explicitly assigned).

val PhotoWidgetLightColorScheme: ColorScheme = lightColorScheme(
    primary = AccentPrimary,
    onPrimary = Cream50,
    primaryContainer = AccentPrimarySubtleLight,
    onPrimaryContainer = Terracotta900,
    secondary = AccentSecondary,
    onSecondary = Cream50,
    secondaryContainer = AccentSecondarySubtleLight,
    onSecondaryContainer = Sage900,
    tertiary = AccentSecondaryHover,
    onTertiary = Cream50,
    tertiaryContainer = AccentSecondarySubtleLight,
    onTertiaryContainer = Sage900,
    background = SurfaceAppLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceCardLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceSunkenLight,
    onSurfaceVariant = TextMutedLight,
    outline = BorderDefaultLight,
    outlineVariant = BorderSubtleLight,
    error = StatusDanger,
    onError = Cream50,
    errorContainer = StatusDangerSubtleBg,
    onErrorContainer = StatusDanger,
    inverseSurface = SurfaceInverse,
    inverseOnSurface = TextInverse,
    scrim = Ink900,
    surfaceTint = Color.Transparent,
)

val PhotoWidgetDarkColorScheme: ColorScheme = darkColorScheme(
    primary = AccentPrimary,
    onPrimary = Cream50,
    primaryContainer = AccentPrimarySubtleDark,
    onPrimaryContainer = Terracotta300,
    secondary = AccentSecondary,
    onSecondary = Cream50,
    secondaryContainer = AccentSecondarySubtleDark,
    onSecondaryContainer = Sage300,
    tertiary = AccentSecondaryHover,
    onTertiary = Cream50,
    tertiaryContainer = AccentSecondarySubtleDark,
    onTertiaryContainer = Sage300,
    background = SurfaceAppDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceCardDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceSunkenDark,
    onSurfaceVariant = TextMutedDark,
    outline = BorderDefaultDark,
    outlineVariant = BorderSubtleDark,
    error = StatusDanger,
    onError = Cream50,
    errorContainer = StatusDanger.copy(alpha = 0.22f),
    onErrorContainer = StatusDanger,
    inverseSurface = SurfaceInverse,
    inverseOnSurface = TextInverse,
    scrim = Ink900,
    surfaceTint = Color.Transparent,
)
