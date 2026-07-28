Pill-shaped segmented switcher with a sliding active pill — e.g. Light/Dark, or a 3-way Square/Round/Polaroid pick.

```jsx
<SegmentedControl options={[{label:'Light',value:'light'},{label:'Dark',value:'dark'}]} value={v} onChange={setV} />
<SegmentedControl fullWidth options={[{label:'Square',value:'sq'},{label:'Round',value:'rd'},{label:'Polaroid',value:'pl'}]} value={v} onChange={setV} />
```

Use `fullWidth` for the 3-wide variant spanning a settings row edge-to-edge; omit it for a compact 2–3 option inline switch.
