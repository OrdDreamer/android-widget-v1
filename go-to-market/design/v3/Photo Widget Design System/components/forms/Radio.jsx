import React from 'react';
export function Radio({ label, checked, onChange, name, disabled }) {
  return React.createElement('label', {
    style: { display: 'inline-flex', alignItems: 'center', gap: 10, cursor: disabled ? 'default' : 'pointer', fontFamily: 'var(--font-body)', opacity: disabled ? 0.5 : 1 },
  },
    React.createElement('span', {
      onClick: () => !disabled && onChange && onChange(),
      style: {
        width: 20, height: 20, borderRadius: '50%', display: 'inline-flex', alignItems: 'center', justifyContent: 'center',
        border: `1.5px solid ${checked ? 'var(--accent-primary)' : 'var(--border-default)'}`,
        transition: 'border-color var(--duration-fast) var(--ease-calm)',
      },
    }, checked && React.createElement('span', { style: { width: 10, height: 10, borderRadius: '50%', background: 'var(--accent-primary)' } })),
    label && React.createElement('span', { style: { fontSize: 'var(--fs-body)', color: 'var(--text-primary)' } }, label));
}
