Centered modal for confirmations, e.g. "Remove this widget?".

```jsx
<Dialog open={open} title="Remove widget?" onClose={close} actions={<><Button variant="ghost" onClick={close}>Cancel</Button><Button variant="primary" onClick={remove}>Remove</Button></>}>
  This photo will stop appearing on your home screen.
</Dialog>
```

Backdrop is a soft blurred ink scrim; the panel is white, generously rounded (--radius-lg), no border.
