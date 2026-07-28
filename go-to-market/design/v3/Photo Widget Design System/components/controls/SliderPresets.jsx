import React from 'react';
export function SliderPresets({ label, value, min = 0, max = 100, presets = [], unit = '', onChange }) {
  const Slider = window.PhotoWidgetDesignSystem_69a50d ? window.PhotoWidgetDesignSystem_69a50d.Slider : null;
  return React.createElement('div', { style: { display: 'flex', flexDirection: 'column', gap: 10, fontFamily: 'var(--font-body)' } },
    Slider ? React.createElement(Slider, { label, value, min, max, onChange }) :
      React.createElement('span', { style: { fontSize: 'var(--fs-body-sm)', color: 'var(--text-secondary)' } }, label),
    React.createElement('div', { style: { display: 'flex', gap: 8 } },
      presets.map(p => {
        const selected = p === value;
        return React.createElement('button', {
          key: p, type: 'button', onClick: () => onChange && onChange(p),
          style: {
            flex: 1, padding: '6px 0', borderRadius: 'var(--radius-sm)', border: `1px solid ${selected ? 'var(--accent-primary)' : 'var(--border-subtle)'}`,
            background: selected ? 'var(--accent-primary-subtle)' : 'var(--surface-card)', color: selected ? 'var(--pw-terracotta-700)' : 'var(--text-muted)',
            fontSize: 'var(--fs-caption)', cursor: 'pointer', fontFamily: 'var(--font-body)',
          },
        }, `${p}${unit}`);
      })));
}
