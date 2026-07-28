Custom drag-to-set slider (no native OS styling) — e.g. photo zoom/crop or caption opacity. Note: widget *size* itself is resized from the Android home screen, not inside the app — don't use this for a size control.

```jsx
<Slider label="Photo zoom" min={100} max={200} value={zoom} onChange={setZoom} />
```
