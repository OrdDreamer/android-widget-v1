# Claude Design prompt — Photo Widget v1

Paste this as the **first message** in Claude Design (or upload + paste).  
Full product spec (if needed later): `design-brief.md`.

---

## Prompt (copy below)

```
Design a polished Android mobile app UI (phone, Material 3) for "Photo Widget" — a simple utility that puts one chosen photo on the home screen as a resizable widget (separate from wallpaper). Multiple widgets are independent.

Brand / tone
- Emotion-first, warm calm — personal photo always visible beside wallpaper (not instead of it)
- Trust, simplicity, human — NOT social (no Locket), NOT aesthetic Widgetsmith clone
- Avoid: neon, overloaded shapes/frames, gamification, purple-default AI look
- Working name: Photo Widget

Audience
- Primary: people who want a partner/child/pet/memory photo always visible on the home screen
- Framing controls exist, but do NOT make the UI look like a heavy customizer

Platform
- Android phone mockups, Material 3
- Light AND Dark (system) — show both for key screens
- Design for long translated labels (EN default strings; leave room for DE/FR)
- Touch targets ≥ 48dp

Monetization (layout only)
- Banner ads IN the app only — never on the home-screen widget itself
- Banner on main screen and on settings screen
- After Save: interstitial (note only; no need to mock the ad creative)
- Ad containers: muted/subtle, never look like primary CTAs; never cover primary buttons

Screens to design (all of them — small app)

1) Main — empty state (no widgets yet)
- Top app bar: "Photo Widget"
- Hero empty state:
  - Headline (emotion): "Keep an important photo in sight"
  - Subhead (wallpaper contrast): "Separate from wallpaper — one photo per widget"
  - Simple illustration/mock: wallpaper + a photo widget on top (not photorealistic)
  - 3 short numbered steps: Add to home screen → Place widget → Choose photo & save
  - Small fallback tip (secondary text): if Add isn't supported — "Long-press home screen → Widgets"
- Primary CTA: "Add to home screen"
- Banner ad zone (bottom or below content; do not cover CTA)
  - Reserve ~50–90dp for adaptive banner; never cover primary CTAs

2) Main — with widgets (2–3 items)
- Same top bar + primary CTA "Add to home screen"
- Section: "Active widgets"
- Each row: thumbnail ~52dp (shape matches widget: rect / rounded / circle) + title (e.g. "Mom" or "Widget #1") + size like "2x2" + actions Edit / Reset
- Banner ad zone; list scrolls if needed

3) Widget settings (single scroll; same layout for in-app edit and launcher configure)
Order top → bottom:
1. Preview — 1:1 square, clipped to selected shape; placeholder "No photo selected"
   - Preview MUST visually match the real home-screen widget (same crop/shape)
   - Contain mode: empty/transparent corners (surface behind), not filled with a dark plate
   - Neutral preview background that works in light AND dark (not hardcoded light gray)
2. "Choose photo" button
3. Optional display name field (max 40 chars) — used only in the app list
4. Rotation ±90° + degree label (only when photo selected)
5. Alignment — 3×3 grid (prefer icons over long text)
6. Scale — Cover | Contain (segmented)
7. Shape — Rectangle | Rounded | Circle (segmented)
8. Corner radius slider 0–48 (only when Rounded)
9. Small info note: launcher may also round outer corners
10. Tap action — Decorative | Open app | Open this widget (3-option segmented; allow 2-line labels)
11. Banner ad zone (~50–90dp; never cover Save)
12. Primary "Save" full width
13. Outlined "Cancel" (in-app edit only — include it on this mock)

Show THREE variants of settings:
- A) No photo yet (hide rotation + alignment)
- B) Photo selected, Rounded shape, slider visible
- C) Photo loading OR load error inside preview (skeleton/progress; or short error + retry via Choose photo)

Product notes for settings:
- Saving without a photo is allowed (placeholder widget) — do not block Save
- After Save: interstitial may show — do not design the ad creative; Save feedback should still feel complete

4) Reset dialog
- Title: "Reset widget"
- Body: clears photo and settings; widget stays on the home screen
- Confirm "Reset" / Cancel

Exact EN copy to prefer (can polish wording slightly, keep meaning)
- Empty headline: "Keep an important photo in sight"
- Empty subhead: "Separate from wallpaper — one photo per widget"
- CTA: "Add to home screen"
- Section: "Active widgets"
- Buttons: "Choose photo", "Save", "Cancel", "Edit", "Reset"
- Preview empty: "No photo selected"
- Scale: Cover | Contain
- Shape: Rectangle | Rounded | Circle
- Tap: Decorative | Open app | Open this widget

Also deliver
- Adaptive app icon concept (emotion-first; not a generic gallery/camera icon)
- Simple design tokens: primary, surfaces, on-surface, info/secondary container — light + dark
- Spacing: 8dp grid, ~16–20dp horizontal padding
- Do NOT use default Material purple (#6750A4) as brand primary

Out of scope (do NOT invent)
- Onboarding carousels, slideshow, albums, lock screen, cloud, accounts, paywall, About/app settings
- Home-screen widget chrome beyond preview accuracy
- Privacy as a hero message (optional tiny note under Choose photo is OK, not required)
- In-widget ads

Output
- Interactive Android phone mockups covering screens 1–4 above
- Light + dark for main empty, main with list, and settings (with photo)
- Include settings variants A/B and at least one of loading or error preview
- Clean, calm UI ready for Google Play v1
```

---

## How to use

1. New project in Claude Design → paste the prompt block.
2. Optional: also upload `design-brief.md` and say: *Use uploaded brief only if something conflicts; prefer this prompt.*
3. Iterate per screen with comments; don’t re-paste the full brief each time.

## Optional follow-ups (after first draft)

- `Tighten empty state: less text, stronger illustration of wallpaper vs widget.`
- `Make alignment a compact icon 3×3, no long labels.`
- `Show German strings on settings to stress-test segmented buttons.`
- `App icon only: 3 variants, warm calm, no camera cliché.`
