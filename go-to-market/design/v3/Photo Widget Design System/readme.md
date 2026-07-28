This is the design system for **Photo Widget**, a calm Android app that lets people keep a meaningful photo on their home screen as its own resizable widget — living beside the wallpaper, not replacing it.

No codebase, Figma file, or existing brand assets were attached for this run. Everything below — palette, type pairing, tone, and the component set — was authored from scratch based on the product description and the user's direction (warm/earthy palette, serif-display + humanist-sans pairing, Material Symbols iconography for the Android platform, quiet/minimal tone). If a real codebase or Figma file exists, attach it and this system should be reconciled against it.

## Content fundamentals

**Voice:** quiet, confident, unhurried. Photo Widget talks like someone who respects your home screen — it never begs for attention, never gamifies, never says "Oops!" It states things plainly and gets out of the way.

- Address the user as **you**; the app itself is rarely "I" — prefer describing what happens ("Your photo updates every morning") over what the app does ("I'll update your photo").
- **Sentence case** everywhere — buttons, headings, settings labels. Never title case, never all-caps except tiny uppercase badges/eyebrows.
- Short sentences. No exclamation points. No emoji in UI copy (see Iconography).
- Examples of the voice:
  - Empty state: "No photo yet. Choose one that means something."
  - Confirmation: "Widget updated."
  - Settings label: "Show caption" / "Update every" / "Frame style"
  - Destructive confirm: "Remove widget? This photo will stop appearing on your home screen." (states the consequence plainly, no scare language)
  - Onboarding: "Pick a photo. Resize it. Let it sit there." (three short clauses, no hard sell)
- Avoid: growth-hacky urgency ("Don't miss out!"), cutesy mascots, "Oops"/"Yikes", excessive exclamation, emoji-as-punctuation.

## Visual foundations

- **Palette:** warm and earthy, not primary-tech. Cream paper background (`--pw-cream-100 #F5F1EA`), near-black warm ink for text (`--pw-ink-900 #211F1B`), terracotta as the single accent used for primary actions and focus (`--pw-terracotta-500 #B4693E`), sage as a secondary/success tone (`--pw-sage-500 #7D8C6C`). No blue, no purple, no neon — the app is about a photo, not a UI.
- **Type:** a two-family pairing. **Lora** (serif, occasionally italic) for display moments — the wordmark, empty-state headlines, dialog titles — giving the app a warm, personal, "handwritten note" feel. **Karla** (humanist sans) for everything functional: body copy, labels, buttons, settings. Both are Google Fonts substitutions (see Iconography/Fonts caveat below) — flag for real font files if the brand has its own.
- **Spacing:** 4px base scale (4/8/12/16/20/24/32/40/48/64/80/96). Generous breathing room; no cramped settings rows.
- **Backgrounds:** flat cream paper, no gradients, no textures, no photography as chrome. The user's own photo is the only imagery — the UI stays out of its way.
- **Corner radii:** soft but not pill-happy — 8px small controls, 14px inputs/buttons, 20px cards, 28px large sheets/dialogs, full pill only for chips/toggles.
- **Shadows:** soft and low-contrast (`--shadow-sm/md/lg`), warm-tinted (ink-based rgba, never pure black), used sparingly — a resting card has almost none, a floating dialog gets the strongest.
- **Borders:** hairline (1px, `--border-subtle`/`--border-default`) rather than shadow-only separation on flat surfaces like inputs and outline buttons.
- **Motion:** calm, no bounce, no springs. One easing (`--ease-calm`, an ease-out curve) at three durations (150/240/420ms). Toasts fade + slide up 8px; nothing overshoots.
- **Hover states:** subtle background tint shift (sunken cream) or a one-step darker fill for solid buttons — never a glow or scale-up beyond icon buttons' 1.06x.
- **Press states:** solid buttons scale to 0.97; no color-only press feedback on primary actions.
- **Transparency/blur:** only on the dialog scrim (`rgba(ink,0.35)` + 2px blur) — never on cards or bars.
- **Photo imagery color vibe:** warm, natural, minimally filtered — the palette is built to sit *behind* a real photo without competing with it (avoid this system's own generic imagery being cool/blue or heavily graded).
- **Cards:** cream-on-cream with a hairline border and a whisper of shadow by default (`Card` `default` variant); pure white + stronger shadow only for genuinely floating content (`raised`); no colored left-border accent trope.
- **Layout:** single-column, generous margins; settings screens are grouped lists of `Card` sections, not dense tables.

## Iconography

- **System:** [Material Symbols (Rounded)](https://fonts.google.com/icons), loaded via Google's CDN stylesheet — chosen to match the Android platform's native icon language and the app's soft/rounded visual language. No custom icon font or SVG sprite exists yet (nothing was provided to copy in).
- Icons are used **functionally only** (settings gears, add-photo, refresh, favorite) — never decoratively, never emoji-as-icon.
- Emoji are not used in the product UI at all, per the quiet/minimal tone.
- **Caveat:** this is a substitution, not a brand-owned icon set. If Photo Widget has its own icon library, attach it and this section (plus every `icon=` prop usage) should be swapped over.

## Fonts caveat

**Lora** and **Karla** are Google Fonts substitutions picked to match the requested "serif display + warm humanist sans" direction — no real font files were provided. `tokens/fonts.css` loads them via a Google Fonts `@import`, not self-hosted files. If the brand has licensed fonts, attach the files and this doc/token file should be updated to `@font-face` them locally.

## Jetpack Compose

Photo Widget is an Android app, so every token here is chosen to drop into a **custom** Compose `MaterialTheme` (`ColorScheme`, `Typography`, `Shapes`) — not Material 3's default look. Full role mapping, Kotlin snippets, and component-to-composable notes: `guidelines/jetpack-compose.md`. Highlights: shadows (not M3 tonal elevation) carry depth; one calm ease-out curve, no `spring()`; spacing tokens are already dp-equivalent (4px = 4dp).

## Logo

No logo or wordmark file was provided. Wherever a mark would go, the brand renders as plain type: "Photo Widget" set in Lora italic. Do not hand-draw a logo — attach one if it exists.

## Index

- `styles.css` — root stylesheet, imports everything under `tokens/`.
- `tokens/` — `colors.css`, `typography.css`, `spacing.css`, `effects.css` (radius/shadow/motion), `fonts.css`.
- `guidelines/` — foundation specimen cards (Colors, Type, Spacing, Brand) shown in the Design System tab.
- `components/core/` — Button, IconButton, Card, Badge, Tag
- `components/forms/` — Input, Select, Checkbox, Radio, Switch
- `components/controls/` — Chip, SegmentedControl, FrameStyleSelector, AlignmentPad, Slider, SliderPresets, RotationControl, ValueReadout, CharacterCounter (widget-customization primitives: frame/rotation/alignment/size pickers)
- `components/media/` — PhotoPreview (empty / error / loaded states)
- `components/feedback/` — Toast, Tooltip, InlineErrorBanner, ProgressIndicator, HelperNote
- `components/navigation/` — Tabs
- `components/overlay/` — Dialog (serves as the Alert Dialog)
- `ui_kits/photo-widget-app/` — click-through recreation of the Android app: onboarding, home screen with widget, widget customization.
- `SKILL.md` — portable skill file for use in Claude Code or other agent contexts.

### Intentional additions
No source defined a component inventory, so the standard set (Button, IconButton, Input, Select, Checkbox, Radio, Switch, Card, Badge, Tag, Tabs, Dialog, Toast, Tooltip) was authored first to cover a typical settings-and-photo app. The user then specified an explicit widget-customization control set, added under `components/controls/`: Chip, SegmentedControl, FrameStyleSelector, AlignmentPad, Slider, SliderPresets, RotationControl, ValueReadout, CharacterCounter — plus InlineErrorBanner, ProgressIndicator, and HelperNote under `components/feedback/`. "Alert Dialog" is served by the existing `Dialog`; "Text Field" by the existing `Input`.
