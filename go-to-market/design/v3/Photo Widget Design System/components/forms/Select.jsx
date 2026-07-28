import React from 'react';
export function Select({ label, value, onChange, options = [] }) {
  return React.createElement('label', { style: { display: 'flex', flexDirection: 'column', gap: 6, fontFamily: 'var(--font-body)' } },
    label && React.createElement('span', { style: { fontSize: 'var(--fs-body-sm)', fontWeight: 'var(--fw-medium)', color: 'var(--text-secondary)' } }, label),
    React.createElement('div', { style: { position: 'relative' } },
      React.createElement('select', {
        value, onChange: e => onChange && onChange(e.target.value),
        style: {
          appearance: 'none', width: '100%', background: 'var(--surface-sunken)', border: '1px solid var(--border-subtle)',
          borderRadius: 'var(--radius-md)', padding: '10px 36px 10px 14px', fontSize: 'var(--fs-body)', color: 'var(--text-primary)',
          font: 'inherit', cursor: 'pointer',
        },
      }, options.map(o => React.createElement('option', { key: o.value, value: o.value }, o.label))),
      React.createElement('span', { className: 'material-symbols-rounded', style: { position: 'absolute', right: 10, top: '50%', transform: 'translateY(-50%)', fontSize: 18, color: 'var(--text-muted)', pointerEvents: 'none' } }, 'expand_more')));
}
