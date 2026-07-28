Visual picker for the widget's frame treatment — thumbnails, not radio text rows, since the shape itself is the choice.

```jsx
<FrameStyleSelector value={shape} onChange={setShape} />
```

Fixed 4-option set: none, rounded, circle, polaroid. Selected thumbnail gets a 2px accent ring.
