The photo preview zone shown while choosing/reviewing the widget's photo — the one place a real image, not chrome, is the point.

```jsx
<PhotoPreview state="empty" onSelect={pickPhoto} />
<PhotoPreview state="error" onRetry={reload} />
<PhotoPreview state="loaded" src={photoUrl} onSelect={pickPhoto} />
```

`empty`: dashed sunken well + "No photo yet". `error`: soft red-tinted well + "Couldn't load this photo" + "Try again". `loaded`: the photo fills the frame with a small floating edit button (blurred dark circle, matches the app's only use of blur). Square by default (`size`); crop/aspect is the consuming screen's job.
