import React from 'react';
export function SegmentedControl({ options = [], value, onChange, fullWidth }) {
  const idx = Math.max(0, options.findIndex(o => o.value === value));
  return React.createElement('div', {
    style: {
      display: fullWidth ? 'flex' : 'inline-flex', position: 'relative', background: 'var(--surface-sunken)',
      borderRadius: 'var(--radius-pill)', padding: 4, gap: 2, width: fullWidth ? '100%' : 'auto',
    },
  },
    React.createElement('div', {
      style: {
        position: 'absolute', top: 4, bottom: 4, left: `calc(${idx} * ${100 / options.length}% + 4px)`,
        width: `calc(${100 / options.length}% - 8px)`, background: 'var(--surface-raised)', borderRadius: 'var(--radius-pill)',
        boxShadow: 'var(--shadow-sm)', transition: 'left var(--duration-base) var(--ease-calm)',
      },
    }),
    options.map(o => React.createElement('button', {
      key: o.value, type: 'button', onClick: () => onChange && onChange(o.value),
      style: {
        position: 'relative', zIndex: 1, flex: fullWidth ? 1 : 'none', border: 'none', background: 'none', cursor: 'pointer',
        font: 'inherit', fontFamily: 'var(--font-body)', fontSize: 'var(--fs-body-sm)',
        fontWeight: o.value === value ? 'var(--fw-semibold)' : 'var(--fw-regular)',
        color: o.value === value ? 'var(--text-primary)' : 'var(--text-muted)',
        padding: '8px 16px', transition: 'color var(--duration-fast) var(--ease-calm)',
      },
    }, o.label)));
}
