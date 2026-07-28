import React from 'react';
export function Tabs({ tabs = [], value, onChange }) {
  return React.createElement('div', { style: { display: 'flex', gap: 'var(--space-2)', borderBottom: '1px solid var(--border-subtle)' } },
    tabs.map(t => {
      const active = t.value === value;
      return React.createElement('button', {
        key: t.value, onClick: () => onChange && onChange(t.value),
        style: {
          background: 'none', border: 'none', cursor: 'pointer', font: 'inherit', fontFamily: 'var(--font-body)',
          fontSize: 'var(--fs-body)', fontWeight: active ? 'var(--fw-semibold)' : 'var(--fw-regular)',
          color: active ? 'var(--text-primary)' : 'var(--text-muted)', padding: '10px 6px', position: 'relative',
          transition: 'color var(--duration-fast) var(--ease-calm)',
        },
      }, t.label, active && React.createElement('span', {
        style: { position: 'absolute', left: 0, right: 0, bottom: -1, height: 2, background: 'var(--accent-primary)', borderRadius: 2 },
      }));
    }));
}
