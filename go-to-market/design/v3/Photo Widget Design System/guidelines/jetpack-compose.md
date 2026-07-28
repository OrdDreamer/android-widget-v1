# Implementing Photo Widget in Jetpack Compose

This system is authored as web tokens, but every value is chosen to drop into a **custom** Compose `MaterialTheme` — never the Material 3 baseline palette/type scale/shapes. Compose gives you `ColorScheme`, `Typography`, and `Shapes` as fully overridable data classes; below is exactly how our tokens fill them in.

## Color → `ColorScheme`

1dp = 1px at mdpi, so the hex values below are copy-paste. Roles are Compose's built-in `ColorScheme` slots, filled with our brand colors instead of Material's defaults — no purple/teal M3 baseline anywhere.

| Compose role | Value | Web token |
|---|---|---|
| `primary` | `#B4693E` | `--accent-primary` |
| `onPrimary` | `#FBF8F3` | `--text-on-accent` |
| `primaryContainer` | `#F3DCCB` | `--accent-primary-subtle` |
| `onPrimaryContainer` | `#5E3018` | `--pw-terracotta-900` |
| `secondary` | `#7D8C6C` | `--accent-secondary` |
| `onSecondary` | `#FBF8F3` | `--text-on-accent` |
| `secondaryContainer` | `#E3E8DD` | `--accent-secondary-subtle` |
| `background` | `#F5F1EA` | `--surface-app` |
| `onBackground` | `#211F1B` | `--text-primary` |
| `surface` | `#FBF8F3` | `--surface-card` |
| `surfaceVariant` | `#ECE4D6` | `--pw-cream-200` |
| `onSurface` | `#211F1B` | `--text-primary` |
| `onSurfaceVariant` | `#6B675E` | `--text-muted` |
| `outline` | `#D6CCB8` | `--border-default` |
| `outlineVariant` | `#E4DDCE` | `--border-subtle` |
| `error` | `#B4453E` | `--status-danger` |
| `inverseSurface` | `#211F1B` | `--surface-inverse` |
| `inverseOnSurface` | `#FBF8F3` | `--text-inverse` |

```kotlin
val PhotoWidgetColorScheme = lightColorScheme(
  primary = Color(0xFFB4693E), onPrimary = Color(0xFFFBF8F3),
  primaryContainer = Color(0xFFF3DCCB), onPrimaryContainer = Color(0xFF5E3018),
  secondary = Color(0xFF7D8C6C), onSecondary = Color(0xFFFBF8F3),
  secondaryContainer = Color(0xFFE3E8DD),
  background = Color(0xFFF5F1EA), onBackground = Color(0xFF211F1B),
  surface = Color(0xFFFBF8F3), surfaceVariant = Color(0xFFECE4D6),
  onSurface = Color(0xFF211F1B), onSurfaceVariant = Color(0xFF6B675E),
  outline = Color(0xFFD6CCB8), outlineVariant = Color(0xFFE4DDCE),
  error = Color(0xFFB4453E),
  inverseSurface = Color(0xFF211F1B), inverseOnSurface = Color(0xFFFBF8F3),
)
```

There is no dark theme defined yet (not requested) — extend this table with dark-tuned tones rather than letting Compose auto-derive one, so the warm cast is preserved in low light.

## Type → `Typography`

Compose's `Typography` slots take our two-family pairing directly — Lora (display, italic-capable) and Karla (everything else) — at our exact sizes/line-heights, not M3's defaults.

```kotlin
val PhotoWidgetTypography = Typography(
  displayLarge = TextStyle(fontFamily = Lora, fontWeight = FontWeight.Medium, fontStyle = FontStyle.Italic, fontSize = 44.sp, lineHeight = 49.sp),
  displayMedium = TextStyle(fontFamily = Lora, fontWeight = FontWeight.Normal, fontSize = 32.sp, lineHeight = 38.sp),
  titleLarge = TextStyle(fontFamily = Lora, fontWeight = FontWeight.Normal, fontSize = 24.sp, lineHeight = 31.sp),
  titleMedium = TextStyle(fontFamily = Karla, fontWeight = FontWeight.SemiBold, fontSize = 19.sp, lineHeight = 27.sp),
  bodyLarge = TextStyle(fontFamily = Karla, fontWeight = FontWeight.Normal, fontSize = 18.sp, lineHeight = 28.sp),
  bodyMedium = TextStyle(fontFamily = Karla, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 25.sp),
  bodySmall = TextStyle(fontFamily = Karla, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 21.sp),
  labelSmall = TextStyle(fontFamily = Karla, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, lineHeight = 17.sp, letterSpacing = 0.5.sp),
)
```

`Lora`/`Karla` are `FontFamily`s built from the same Google Fonts substitutions flagged in the root readme — swap in real font files as `Font(R.font.*)` resources if the brand licenses its own.

## Shape → `Shapes`

```kotlin
val PhotoWidgetShapes = Shapes(
  extraSmall = RoundedCornerShape(8.dp),
  small = RoundedCornerShape(14.dp),
  medium = RoundedCornerShape(20.dp),
  large = RoundedCornerShape(28.dp),
  extraLarge = RoundedCornerShape(percent = 50), // pill, for chips/toggles
)
```

## Elevation

The brand explicitly avoids Material 3's tonal-elevation color shift (surfaces tinting toward primary as they rise) — it reads as "Android default," not calm/paper-like. Use real drop shadows instead, sized to our scale:

```kotlin
object PhotoWidgetElevation {
  val sm = 2.dp   // Modifier.shadow(sm, ambientColor = InkShadow, spotColor = InkShadow)
  val md = 8.dp
  val lg = 20.dp
}
val InkShadow = Color(0xFF211F1B).copy(alpha = 0.10f) // warm-tinted, never pure black
```

Apply via `Modifier.shadow(elevation = PhotoWidgetElevation.md, shape = PhotoWidgetShapes.medium, ambientColor = InkShadow, spotColor = InkShadow)` — keep surfaces flat-colored (no tonalElevation param) and let the shadow carry depth, matching the "soft, low-contrast, warm-tinted" rule in the root readme.

## Motion

Compose's `AnimationSpec`s should use one calm ease-out curve at our three durations — no `spring()` (bounce), per brand:

```kotlin
val CalmEasing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)
val fast = tween<Float>(150, easing = CalmEasing)
val base = tween<Float>(240, easing = CalmEasing)
val slow = tween<Float>(420, easing = CalmEasing)
```

## Spacing

Our 4px scale is already dp-equivalent: use `4.dp, 8.dp, 12.dp, 16.dp, 20.dp, 24.dp, 32.dp, 40.dp, 48.dp, 64.dp, 80.dp, 96.dp` directly — no conversion needed, just don't hardcode `px`-flavored magic numbers elsewhere in the codebase.

## Components → Compose

Every primitive in `components/` maps to a *custom-styled* Compose composable built on the base Material 3 building block (never the default `MaterialTheme` look):

- `Button` → `Button`/`OutlinedButton`/`TextButton` with `shape = PhotoWidgetShapes.small`, `colors = ButtonDefaults.buttonColors(containerColor = primary/secondary, contentColor = onPrimary)`, scale-down press handled via a custom `Modifier.pointerInput` (Compose has no built-in press-scale).
- `IconButton` → `IconButton` in a `Surface` circle for the `filled`/`accent` variants (Compose's plain `IconButton` has no built-in background).
- `Card` → `Surface` (not `Card`, to skip M3's default tonal elevation) with explicit `shape`, `color`, and `Modifier.shadow(...)`.
- `Input`/`Select` → `OutlinedTextField`/`ExposedDropdownMenuBox` with `colors = OutlinedTextFieldDefaults.colors(...)` remapped to our sunken-well look (no floating M3 label animation — keep the static label above, like our web version).
- `Checkbox`/`Radio`/`Switch` → Compose's built-ins, recolored via `CheckboxDefaults`/`RadioButtonDefaults`/`SwitchDefaults` to our primary/border tokens.
- `Tabs` → `TabRow` with `indicator` restyled to our thin accent underline (no M3 pill indicator).
- `Dialog` → `Dialog` + custom `Surface` content (skip `AlertDialog`'s default M3 chrome).
- `Toast`/`Tooltip` → `Snackbar`/`PlainTooltip` recolored to `inverseSurface`/`inverseOnSurface`.
