import React from 'react';
export function Slider({ value = 0, min = 0, max = 100, onChange, label }) {
  const trackRef = React.useRef(null);
  const pct = Math.max(0, Math.min(1, (value - min) / (max - min)));
  const setFromClientX = clientX => {
    const rect = trackRef.current.getBoundingClientRect();
    const ratio = Math.max(0, Math.min(1, (clientX - rect.left) / rect.width));
    onChange && onChange(Math.round((min + ratio * (max - min)) * 100) / 100);
  };
  const onPointerDown = e => {
    setFromClientX(e.clientX);
    const move = ev => setFromClientX(ev.clientX);
    const up = () => { window.removeEventListener('pointermove', move); window.removeEventListener('pointerup', up); };
    window.addEventListener('pointermove', move); window.addEventListener('pointerup', up);
  };
  return React.createElement('div', { style: { display: 'flex', flexDirection: 'column', gap: 8, fontFamily: 'var(--font-body)' } },
    label && React.createElement('span', { style: { fontSize: 'var(--fs-body-sm)', fontWeight: 'var(--fw-medium)', color: 'var(--text-secondary)' } }, label),
    React.createElement('div', {
      ref: trackRef, onPointerDown,
      style: { position: 'relative', height: 24, display: 'flex', alignItems: 'center', cursor: 'pointer', touchAction: 'none' },
    },
      React.createElement('div', { style: { position: 'absolute', left: 0, right: 0, height: 6, borderRadius: 'var(--radius-pill)', background: 'var(--border-subtle)' } }),
      React.createElement('div', { style: { position: 'absolute', left: 0, width: `${pct * 100}%`, height: 6, borderRadius: 'var(--radius-pill)', background: 'var(--accent-primary)' } }),
      React.createElement('div', {
        style: {
          position: 'absolute', left: `calc(${pct * 100}% - 10px)`, top: 2, width: 20, height: 20, borderRadius: '50%',
          background: '#fff', boxShadow: 'var(--shadow-md)', border: '1px solid var(--border-subtle)',
        },
      })));
}
