import React from 'react';
export function Input({ label, placeholder, value, onChange, type = 'text', icon, error, disabled }) {
  const [focused, setFocused] = React.useState(false);
  return React.createElement('label', { style: { display: 'flex', flexDirection: 'column', gap: 6, fontFamily: 'var(--font-body)' } },
    label && React.createElement('span', { style: { fontSize: 'var(--fs-body-sm)', fontWeight: 'var(--fw-medium)', color: 'var(--text-secondary)' } }, label),
    React.createElement('div', {
      style: {
        display: 'flex', alignItems: 'center', gap: 8, background: 'var(--surface-sunken)',
        border: `1px solid ${error ? 'var(--status-danger)' : (focused ? 'var(--accent-primary)' : 'var(--border-subtle)')}`,
        borderRadius: 'var(--radius-md)', padding: '10px 14px', transition: 'border-color var(--duration-fast) var(--ease-calm)',
        opacity: disabled ? 0.5 : 1,
      },
    },
      icon && React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 18, color: 'var(--text-muted)' } }, icon),
      React.createElement('input', {
        type, value, placeholder, disabled,
        onChange: e => onChange && onChange(e.target.value),
        onFocus: () => setFocused(true), onBlur: () => setFocused(false),
        style: { border: 'none', outline: 'none', background: 'transparent', font: 'inherit', fontSize: 'var(--fs-body)', color: 'var(--text-primary)', width: '100%' },
      })),
    error && React.createElement('span', { style: { fontSize: 'var(--fs-caption)', color: 'var(--status-danger)' } }, error));
}
