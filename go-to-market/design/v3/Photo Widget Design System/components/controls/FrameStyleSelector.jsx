import React from 'react';
const SHAPES = [
  { value: 'none', label: 'None', radius: '0px' },
  { value: 'rounded', label: 'Rounded', radius: 'var(--radius-lg)' },
  { value: 'circle', label: 'Circle', radius: '50%' },
  { value: 'polaroid', label: 'Polaroid', radius: '2px' },
];
export function FrameStyleSelector({ value, onChange }) {
  return React.createElement('div', { style: { display: 'flex', gap: 14 } },
    SHAPES.map(s => {
      const selected = s.value === value;
      return React.createElement('button', {
        key: s.value, type: 'button', onClick: () => onChange && onChange(s.value),
        style: { display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 8, background: 'none', border: 'none', cursor: 'pointer', fontFamily: 'var(--font-body)' },
      },
        React.createElement('div', {
          style: {
            width: 56, height: 56, padding: s.value === 'polaroid' ? 6 : 0, boxSizing: 'border-box',
            background: s.value === 'polaroid' ? '#fff' : 'transparent', borderRadius: s.value === 'polaroid' ? 4 : 0,
            boxShadow: selected ? '0 0 0 2px var(--accent-primary), var(--shadow-sm)' : 'var(--shadow-sm)',
          },
        }, React.createElement('div', {
          style: { width: '100%', height: '100%', borderRadius: s.radius, background: 'linear-gradient(135deg,#caa27a,#8a6a52)' },
        })),
        React.createElement('span', { style: { fontSize: 'var(--fs-caption)', color: selected ? 'var(--text-primary)' : 'var(--text-muted)', fontWeight: selected ? 'var(--fw-semibold)' : 'var(--fw-regular)' } }, s.label));
    }));
}
