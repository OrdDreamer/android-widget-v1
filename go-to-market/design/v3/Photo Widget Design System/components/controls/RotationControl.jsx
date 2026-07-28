import React from 'react';
const STEP = 45;
function normalize(deg) { return ((deg % 360) + 360) % 360; }
function snap(deg) { return normalize(Math.round(normalize(deg) / STEP) * STEP); }
export function RotationControl({ value = 0, onChange }) {
  const dialRef = React.useRef(null);
  const setFromClientXY = (clientX, clientY) => {
    const rect = dialRef.current.getBoundingClientRect();
    const cx = rect.left + rect.width / 2, cy = rect.top + rect.height / 2;
    const deg = Math.atan2(clientX - cx, -(clientY - cy)) * (180 / Math.PI);
    onChange && onChange(snap(deg));
  };
  const onPointerDown = e => {
    setFromClientXY(e.clientX, e.clientY);
    const move = ev => setFromClientXY(ev.clientX, ev.clientY);
    const up = () => { window.removeEventListener('pointermove', move); window.removeEventListener('pointerup', up); };
    window.addEventListener('pointermove', move); window.addEventListener('pointerup', up);
  };
  const step = d => onChange && onChange(normalize(value + d));
  return React.createElement('div', { style: { display: 'flex', alignItems: 'center', gap: 16, fontFamily: 'var(--font-body)' } },
    React.createElement('button', { type: 'button', onClick: () => step(-STEP), 'aria-label': 'Rotate left 45°', style: { width: 32, height: 32, borderRadius: '50%', border: '1px solid var(--border-default)', background: 'var(--surface-card)', cursor: 'pointer', display: 'flex', alignItems: 'center', justifyContent: 'center' } },
      React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 16 } }, 'rotate_left')),
    React.createElement('div', {
      ref: dialRef, onPointerDown,
      style: { width: 64, height: 64, borderRadius: '50%', background: 'var(--surface-sunken)', position: 'relative', cursor: 'grab', touchAction: 'none' },
    },
      [0, 45, 90, 135, 180, 225, 270, 315].map(tick => React.createElement('div', {
        key: tick,
        style: {
          position: 'absolute', top: '50%', left: '50%', width: 4, height: 4, borderRadius: '50%',
          background: tick === value ? 'var(--accent-primary)' : 'var(--border-strong)',
          transform: `rotate(${tick}deg) translate(0,-26px)`,
        },
      })),
      React.createElement('div', {
        style: { position: 'absolute', top: '50%', left: '50%', width: 22, height: 4, borderRadius: 2, background: 'var(--accent-primary)', transformOrigin: 'left center', transform: `translate(0,-2px) rotate(${value - 90}deg)` },
      }),
      React.createElement('div', { style: { position: 'absolute', inset: 6, border: '1px solid var(--border-subtle)', borderRadius: '50%' } })),
    React.createElement('button', { type: 'button', onClick: () => step(STEP), 'aria-label': 'Rotate right 45°', style: { width: 32, height: 32, borderRadius: '50%', border: '1px solid var(--border-default)', background: 'var(--surface-card)', cursor: 'pointer', display: 'flex', alignItems: 'center', justifyContent: 'center' } },
      React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 16 } }, 'rotate_right')),
    React.createElement('span', { style: { fontSize: 'var(--fs-body)', color: 'var(--text-primary)', fontWeight: 'var(--fw-semibold)', minWidth: 40 } }, `${value}°`));
}
