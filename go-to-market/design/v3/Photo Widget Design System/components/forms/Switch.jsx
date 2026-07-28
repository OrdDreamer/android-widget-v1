import React from 'react';
export function Switch({ checked, onChange, label, disabled }) {
  return React.createElement('label', {
    style: { display: 'inline-flex', alignItems: 'center', gap: 10, cursor: disabled ? 'default' : 'pointer', fontFamily: 'var(--font-body)', opacity: disabled ? 0.5 : 1 },
  },
    label && React.createElement('span', { style: { fontSize: 'var(--fs-body)', color: 'var(--text-primary)' } }, label),
    React.createElement('span', {
      onClick: () => !disabled && onChange && onChange(!checked),
      style: {
        width: 42, height: 24, borderRadius: 'var(--radius-pill)', position: 'relative', flexShrink: 0,
        background: checked ? 'var(--accent-primary)' : 'var(--border-default)',
        transition: 'background var(--duration-fast) var(--ease-calm)',
      },
    }, React.createElement('span', {
      style: {
        position: 'absolute', top: 3, left: checked ? 21 : 3, width: 18, height: 18, borderRadius: '50%',
        background: '#fff', boxShadow: 'var(--shadow-sm)', transition: 'left var(--duration-fast) var(--ease-calm)',
      },
    })));
}
