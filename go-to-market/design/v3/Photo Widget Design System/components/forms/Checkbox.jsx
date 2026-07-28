import React from 'react';
export function Checkbox({ label, checked, onChange, disabled }) {
  return React.createElement('label', {
    style: { display: 'inline-flex', alignItems: 'center', gap: 10, cursor: disabled ? 'default' : 'pointer', fontFamily: 'var(--font-body)', opacity: disabled ? 0.5 : 1 },
  },
    React.createElement('span', {
      onClick: () => !disabled && onChange && onChange(!checked),
      style: {
        width: 20, height: 20, borderRadius: 6, display: 'inline-flex', alignItems: 'center', justifyContent: 'center',
        border: `1.5px solid ${checked ? 'var(--accent-primary)' : 'var(--border-default)'}`,
        background: checked ? 'var(--accent-primary)' : 'transparent',
        transition: 'background var(--duration-fast) var(--ease-calm), border-color var(--duration-fast) var(--ease-calm)',
      },
    }, checked && React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 15, color: 'var(--text-on-accent)' } }, 'check')),
    label && React.createElement('span', { style: { fontSize: 'var(--fs-body)', color: 'var(--text-primary)' } }, label));
}
