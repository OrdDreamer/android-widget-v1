@file:OptIn(ExperimentalTextApi::class)

package com.photowidget.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.photowidget.R

// Lora (serif, display moments) + a humanist sans for everything else — the design system's
// two-family pairing (see "Photo Widget Design System/readme.md" § Type), shipped as OFL-licensed
// variable fonts sourced from github.com/google/fonts (no font files were included in the
// handoff). The design system's own sans pick, Karla, has no Cyrillic glyphs at all — confirmed
// both in its shipped TTF and in Google Fonts' own hosted build (checked its `@font-face`
// unicode-range in the live mock: only Latin ranges). Every Cyrillic character in this
// Ukrainian-only app was silently falling back to whatever sans the OS ships, varying the look
// device to device. Manrope replaces it: full Cyrillic + Latin coverage in one variable file, same
// weight range, comparable geometric-humanist character.

private fun weight(value: Int) = FontVariation.Settings(FontVariation.weight(value))

val Lora = FontFamily(
    Font(R.font.lora, FontWeight.Normal, variationSettings = weight(400)),
    Font(R.font.lora, FontWeight.Medium, variationSettings = weight(500)),
    Font(R.font.lora, FontWeight.SemiBold, variationSettings = weight(600)),
    Font(R.font.lora_italic, FontWeight.Normal, FontStyle.Italic, variationSettings = weight(400)),
    Font(R.font.lora_italic, FontWeight.Medium, FontStyle.Italic, variationSettings = weight(500)),
)

val Manrope = FontFamily(
    Font(R.font.manrope, FontWeight.Normal, variationSettings = weight(400)),
    Font(R.font.manrope, FontWeight.Medium, variationSettings = weight(500)),
    Font(R.font.manrope, FontWeight.SemiBold, variationSettings = weight(600)),
    Font(R.font.manrope, FontWeight.Bold, variationSettings = weight(700)),
)

// Role table verbatim from "Photo Widget Design System/guidelines/jetpack-compose.md" §
// "Type → Typography" (its Compose-specific Kotlin snippet takes precedence over the web
// tokens.css sizes where the two disagree, since this is the Android-specific guidance).
val PhotoWidgetTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Italic,
        fontSize = 44.sp,
        lineHeight = 49.sp,
        letterSpacing = (-0.01f).em,
    ),
    displayMedium = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 38.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 31.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        lineHeight = 27.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.5.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),
)
