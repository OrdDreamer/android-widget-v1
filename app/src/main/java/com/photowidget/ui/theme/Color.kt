package com.photowidget.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Every value below is the exact sRGB conversion of the oklch(...) token used at the same
// spot in "Photo Widget App - Vibrant.dc.html" (OKLab -> linear sRGB, D65), not an approximation.

val ScreenBgTopLight = Color(0xFFF0F5FF)
val ScreenBgBottomLight = Color(0xFFEAE9F8)
val ScreenBgTopDark = Color(0xFF121522)
val ScreenBgBottomDark = Color(0xFF0B0917)

val TitleGradStartLight = Color(0xFF1E49B7)
val TitleGradEndLight = Color(0xFF802BAF)
val TitleGradStartDark = Color(0xFF89B5FF)
val TitleGradEndDark = Color(0xFFDD9FFF)

val OnSurfaceLight = Color(0xFF151A29)
val OnSurfaceDark = Color(0xFFE2E4EB)
val OnSurfaceVariantLight = Color(0xFF545669)
val OnSurfaceVariantDark = Color(0xFF8F919F)

val IconBoxStartLight = Color(0xFFCDDEFF)
val IconBoxEndLight = Color(0xFFDCC2F1)
val IconBoxStartDark = Color(0xFF2C416D)
val IconBoxEndDark = Color(0xFF3B235D)

val BrandAccentLight = Color(0xFF3969D9)
val BrandAccentDark = Color(0xFFA0BEF9)

val CtaStartLight = Color(0xFF49A9FF)
val CtaEndLight = Color(0xFFB24DC8)
val CtaStartDark = Color(0xFF2784D5)
val CtaEndDark = Color(0xFF882E9B)

// Hero illustration colors live baked into empty_state_hero.png /
// drawable-night-nodpi/empty_state_hero.png (generated from these same oklch values) rather
// than as brushes here — see the raster asset for why.

val StepBadgeStartLight = Color(0xFF4BA3F7)
val StepBadgeEndLight = Color(0xFF9867E1)
val StepBadgeStartDark = Color(0xFF1F74BF)
val StepBadgeEndDark = Color(0xFF6635A3)

val AdBannerBgLight = Color(0xFFDADCF2)
val AdBannerBgDark = Color(0xFF22232E)

val CardSurfaceLight = Color(0xFFFDFCF7)
val CardSurfaceDark = Color(0xFF1B1F29)

val OutlineLight = Color(0xFFCBCDDB)
val OutlineDark = Color(0xFF40414D)

val EditIconTintLight = Color(0xFF382D93)
val EditIconTintDark = Color(0xFFB8BEFE)

val AccentGradStartLight = Color(0xFF60AAF3)
val AccentGradEndLight = Color(0xFFAA6CD4)
val AccentGradStartDark = Color(0xFF1F74BF)
val AccentGradEndDark = Color(0xFF7C298E)

val PillTrackBgLight = Color(0xFFE1E3F2)
val PillTrackBgDark = Color(0xFF272833)

val DividerLight = Color(0xFFE6E7EF)
val DividerDark = Color(0xFF323238)

val DashedBoxBgStartLight = Color(0xFFC7D8F9)
val DashedBoxBgEndLight = Color(0xFFD9BEED)
val DashedBoxBgStartDark = Color(0xFF233251)
val DashedBoxBgEndDark = Color(0xFF2F1D4A)
val DashedBoxBorderLight = Color(0xFF979BC4)
val DashedBoxBorderDark = Color(0xFF4F5276)

val ChoosePhotoTextLight = Color(0xFF31248A)
val ChoosePhotoTextDark = Color(0xFFA7C4FF)

val ErrorBgLight = Color(0xFFFFEBE8)
val ErrorBgDark = Color(0xFF3A211F)
val ErrorAccentLight = Color(0xFFCC243D)
val ErrorAccentDark = Color(0xFFFF8D8F)

val FrameClassicStartLight = Color(0xFFAABEE5)
val FrameClassicEndLight = Color(0xFFB2A0D6)
val FrameClassicStartDark = Color(0xFF34476E)
val FrameClassicEndDark = Color(0xFF402B5F)

val FrameMinimalStartLight = Color(0xFFB4C5E5)
val FrameMinimalEndLight = Color(0xFFB8A8D8)
val FrameMinimalStartDark = Color(0xFF3C4D6E)
val FrameMinimalEndDark = Color(0xFF443261)

val FrameVintageStartLight = Color(0xFFE6C58F)
val FrameVintageEndLight = Color(0xFFECA57D)
val FrameVintageStartDark = Color(0xFF6C4C02)
val FrameVintageEndDark = Color(0xFF6E2B00)

val FramePolaroidInnerStartLight = Color(0xFF96BBE3)
val FramePolaroidInnerEndLight = Color(0xFFAB87C7)
val FramePolaroidInnerStartDark = Color(0xFF305880)
val FramePolaroidInnerEndDark = Color(0xFF542E6D)

// Material color scheme -----------------------------------------------------------------

val LightPrimary = BrandAccentLight
val LightOnPrimary = Color(0xFFFFFFFF)
val LightPrimaryContainer = IconBoxStartLight
val LightOnPrimaryContainer = EditIconTintLight
val LightSecondaryContainer = Color(0xFFEDE8F5)
val LightOnSecondaryContainer = Color(0xFF3A2F55)
val LightBackground = ScreenBgTopLight
val LightSurface = CardSurfaceLight
val LightOnSurface = OnSurfaceLight
val LightSurfaceVariant = Color(0xFFF3F0FA)
val LightOnSurfaceVariant = OnSurfaceVariantLight
val LightOutline = OutlineLight
val LightOutlineVariant = DividerLight
val LightError = ErrorAccentLight
val LightOnError = Color(0xFFFFFFFF)

val DarkPrimary = BrandAccentDark
val DarkOnPrimary = Color(0xFF17284A)
val DarkPrimaryContainer = IconBoxStartDark
val DarkOnPrimaryContainer = EditIconTintDark
val DarkSecondaryContainer = Color(0xFF3A3350)
val DarkOnSecondaryContainer = Color(0xFFE6E0F2)
val DarkBackground = ScreenBgTopDark
val DarkSurface = CardSurfaceDark
val DarkOnSurface = OnSurfaceDark
val DarkSurfaceVariant = Color(0xFF2C2738)
val DarkOnSurfaceVariant = OnSurfaceVariantDark
val DarkOutline = OutlineDark
val DarkOutlineVariant = DividerDark
val DarkError = ErrorAccentDark
val DarkOnError = Color(0xFF3A211F)

// Brushes ---------------------------------------------------------------------------------

@Composable
fun brandTitleBrush(): Brush = Brush.horizontalGradient(
    colors = if (isSystemInDarkTheme()) {
        listOf(TitleGradStartDark, TitleGradEndDark)
    } else {
        listOf(TitleGradStartLight, TitleGradEndLight)
    },
)

@Composable
fun brandPrimaryBrush(): Brush = Brush.horizontalGradient(
    colors = if (isSystemInDarkTheme()) {
        listOf(CtaStartDark, CtaEndDark)
    } else {
        listOf(CtaStartLight, CtaEndLight)
    },
)

@Composable
fun accentGradientBrush(): Brush = Brush.linearGradient(
    colors = if (isSystemInDarkTheme()) {
        listOf(AccentGradStartDark, AccentGradEndDark)
    } else {
        listOf(AccentGradStartLight, AccentGradEndLight)
    },
)

/** Tint for icons sitting inside an [iconBoxBrush] tile: settings gear, edit pencil, placeholder glyph. */
@Composable
fun brandIconTint(): Color = if (isSystemInDarkTheme()) EditIconTintDark else EditIconTintLight

@Composable
fun iconBoxBrush(): Brush = Brush.linearGradient(
    colors = if (isSystemInDarkTheme()) {
        listOf(IconBoxStartDark, IconBoxEndDark)
    } else {
        listOf(IconBoxStartLight, IconBoxEndLight)
    },
)

@Composable
fun stepBadgeBrush(): Brush = Brush.linearGradient(
    colors = if (isSystemInDarkTheme()) {
        listOf(StepBadgeStartDark, StepBadgeEndDark)
    } else {
        listOf(StepBadgeStartLight, StepBadgeEndLight)
    },
)

@Composable
fun dashedBoxBackgroundBrush(): Brush = Brush.linearGradient(
    colors = if (isSystemInDarkTheme()) {
        listOf(DashedBoxBgStartDark, DashedBoxBgEndDark)
    } else {
        listOf(DashedBoxBgStartLight, DashedBoxBgEndLight)
    },
)

@Composable
fun dashedBoxBorderColor(): Color = if (isSystemInDarkTheme()) DashedBoxBorderDark else DashedBoxBorderLight

@Composable
fun choosePhotoTextColor(): Color = if (isSystemInDarkTheme()) ChoosePhotoTextDark else ChoosePhotoTextLight

@Composable
fun listThumbnailPlaceholderBorderColor(): Color =
    (if (isSystemInDarkTheme()) BrandAccentDark else BrandAccentLight).copy(alpha = 0.5f)

@Composable
fun adBannerBackgroundColor(): Color = if (isSystemInDarkTheme()) AdBannerBgDark else AdBannerBgLight

@Composable
fun pillTrackBackgroundColor(): Color = if (isSystemInDarkTheme()) PillTrackBgDark else PillTrackBgLight

@Composable
fun errorContainerColor(): Color = if (isSystemInDarkTheme()) ErrorBgDark else ErrorBgLight

@Composable
fun errorAccentColor(): Color = if (isSystemInDarkTheme()) ErrorAccentDark else ErrorAccentLight

@Composable
fun frameSwatchBrush(style: com.photowidget.data.FrameStyle): Brush {
    val dark = isSystemInDarkTheme()
    val (start, end) = when (style) {
        com.photowidget.data.FrameStyle.CLASSIC ->
            if (dark) FrameClassicStartDark to FrameClassicEndDark else FrameClassicStartLight to FrameClassicEndLight
        com.photowidget.data.FrameStyle.MINIMAL ->
            if (dark) FrameMinimalStartDark to FrameMinimalEndDark else FrameMinimalStartLight to FrameMinimalEndLight
        com.photowidget.data.FrameStyle.VINTAGE ->
            if (dark) FrameVintageStartDark to FrameVintageEndDark else FrameVintageStartLight to FrameVintageEndLight
        com.photowidget.data.FrameStyle.POLAROID ->
            if (dark) {
                FramePolaroidInnerStartDark to FramePolaroidInnerEndDark
            } else {
                FramePolaroidInnerStartLight to FramePolaroidInnerEndLight
            }
    }
    return Brush.linearGradient(colors = listOf(start, end))
}

fun screenBackgroundBrush(dark: Boolean): Brush = if (dark) {
    Brush.verticalGradient(colors = listOf(ScreenBgTopDark, ScreenBgBottomDark))
} else {
    Brush.verticalGradient(colors = listOf(ScreenBgTopLight, ScreenBgBottomLight))
}
