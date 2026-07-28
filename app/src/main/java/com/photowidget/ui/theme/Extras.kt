package com.photowidget.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Design tokens with no matching slot in Compose's M3 ColorScheme (surface-raised is distinct
// from surface/surfaceVariant, border-strong, status success/warning, and the warm shadow
// tint). Exposed as theme-reactive composables, mirroring this file's pre-rebuild convention.

@Composable
fun surfaceRaised(): Color = if (isSystemInDarkTheme()) SurfaceRaisedDark else SurfaceRaisedLight

@Composable
fun borderStrong(): Color = if (isSystemInDarkTheme()) BorderStrongDark else BorderStrongLight

@Composable
fun accentPrimaryHover(): Color = AccentPrimaryHover

@Composable
fun accentSecondaryHover(): Color = AccentSecondaryHover

@Composable
fun focusRing(): Color = FocusRing

@Composable
fun statusSuccess(): Color = StatusSuccess

@Composable
fun statusWarning(): Color = StatusWarning

@Composable
fun statusDangerSubtleBorder(): Color = StatusDangerSubtleBorder

@Composable
fun shadowTint(): Color = if (isSystemInDarkTheme()) ShadowTintDark else ShadowTintLight
