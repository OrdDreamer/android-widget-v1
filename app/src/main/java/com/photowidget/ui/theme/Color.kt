package com.photowidget.ui.theme

import androidx.compose.ui.graphics.Color

// Raw palette, exact hex from the "Photo Widget Design System" v3 token manifest
// (go-to-market/design/v3/Photo Widget Design System/_ds_manifest.json). Light values are the
// system's only defined theme; dark values are reverse-engineered from the identical inline
// `dark = {...}` palette repeated in every screen mock under
// "Photo Widget дизайн-handoff/photo-widget/project/*.dc.html" — the only dark-mode spec that
// exists for this design.

val Cream50 = Color(0xFFFBF8F3)
val Cream100 = Color(0xFFF5F1EA)
val Cream200 = Color(0xFFECE4D6)
val Cream300 = Color(0xFFE0D5C0)

val Ink900 = Color(0xFF211F1B)
val Ink700 = Color(0xFF3D3A34)
val Ink500 = Color(0xFF6B675E)
val Ink300 = Color(0xFFA8A296)

val Terracotta100 = Color(0xFFF3DCCB)
val Terracotta300 = Color(0xFFD99A6C)
val Terracotta500 = Color(0xFFB4693E)
val Terracotta700 = Color(0xFF8C4C29)
val Terracotta900 = Color(0xFF5E3018)

val Sage100 = Color(0xFFE3E8DD)
val Sage300 = Color(0xFFACBC9C)
val Sage500 = Color(0xFF7D8C6C)
val Sage700 = Color(0xFF5C6850)
val Sage900 = Color(0xFF3A4232)

val StatusRed500 = Color(0xFFB4453E)
val StatusAmber500 = Color(0xFFC99A3E)

// Two inconsistent non-tokenized "danger tint" hexes appear across the web components
// (Badge danger bg #F2DEDC vs PhotoPreview-error/InlineErrorBanner #F6E4E2 + border #E3B9B4) —
// normalized here to one pair used everywhere in the Compose rebuild.
val StatusDangerSubtleBgLight = Color(0xFFF6E4E2)
val StatusDangerSubtleBorderLight = Color(0xFFE3B9B4)

val DarkBg = Color(0xFF201D18)
val DarkCard = Color(0xFF2B2823)
val DarkSunken = Color(0xFF272319)
val DarkBorder = Color(0xFF3A362E)
val DarkBorderStrong = Color(0xFF5A5548)
val DarkRaised = Color(0xFF332F28)
val DarkTextPrimary = Color(0xFFF5F1EA)
val DarkTextSecondary = Color(0xFFD8D2C4)
val DarkTextMuted = Color(0xFFB4AC9B)

// Semantic tokens -------------------------------------------------------------------------

val SurfaceAppLight = Cream100
val SurfaceAppDark = DarkBg
val SurfaceCardLight = Cream50
val SurfaceCardDark = DarkCard
val SurfaceSunkenLight = Cream200
val SurfaceSunkenDark = DarkSunken
val SurfaceRaisedLight = Color(0xFFFFFFFF)
val SurfaceRaisedDark = DarkRaised

val TextPrimaryLight = Ink900
val TextPrimaryDark = DarkTextPrimary
val TextSecondaryLight = Ink700
val TextSecondaryDark = DarkTextSecondary
val TextMutedLight = Ink500
val TextMutedDark = DarkTextMuted

val BorderSubtleLight = Color(0xFFE4DDCE)
val BorderSubtleDark = DarkBorder
val BorderDefaultLight = Color(0xFFD6CCB8)
val BorderDefaultDark = DarkBorder
val BorderStrongLight = Ink300
val BorderStrongDark = DarkBorderStrong

// The design system never overrides accent/surface-inverse/focus-ring for dark screens (its
// per-mock `darkTokenVars` swap only touches surface/text/border roles) — these stay constant
// across themes by design intent, not oversight.
val AccentPrimary = Terracotta500
val AccentPrimaryHover = Terracotta700
val AccentPrimarySubtleLight = Terracotta100
// Mirrors the one concrete dark-accent-tint example in the mocks (Home Empty step-number
// badge): `rgba(217,154,108,0.18)` = Terracotta300 at 18% alpha over a dark surface.
val AccentPrimarySubtleDark = Terracotta300.copy(alpha = 0.18f)
val AccentSecondary = Sage500
val AccentSecondaryHover = Sage700
val AccentSecondarySubtleLight = Sage100
val AccentSecondarySubtleDark = Sage300.copy(alpha = 0.18f)

val FocusRing = Terracotta500

val StatusSuccess = Sage700
val StatusWarning = StatusAmber500
val StatusDanger = StatusRed500
val StatusDangerSubtleBg = StatusDangerSubtleBgLight
val StatusDangerSubtleBorder = StatusDangerSubtleBorderLight

// Toast/Tooltip intentionally stay ink-on-cream regardless of app theme (the token
// `--surface-inverse` is defined once and never swapped by the mocks' dark overrides).
val SurfaceInverse = Ink900
val TextInverse = Cream50

// Warm-tinted shadow color (never pure black), per guidelines/jetpack-compose.md's Elevation
// section — alpha varies by elevation tier, applied in Elevation.kt.
val ShadowTintLight = Ink900
val ShadowTintDark = Color(0xFF000000)
