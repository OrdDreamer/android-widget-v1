import React from 'react';
export function IconButton({ icon, size = 'md', variant = 'ghost', disabled, onClick, label }) {
  const dims = { sm: 32, md: 40, lg: 48 };
  const fs = { sm: 18, md: 20, lg: 24 };
  const variants = {
    ghost: { background: 'transparent', color: 'var(--text-primary)' },
    filled: { background: 'var(--surface-raised)', color: 'var(--text-primary)', boxShadow: 'var(--shadow-sm)' },
    accent: { background: 'var(--accent-primary-subtle)', color: 'var(--accent-primary-hover)' },
  };
  const [isHover, setHover] = React.useState(false);
  const d = dims[size];
  return React.createElement('button', {
    type: 'button', disabled, onClick, 'aria-label': label,
    onMouseEnter: () => setHover(true), onMouseLeave: () => setHover(false),
    style: {
      width: d, height: d, borderRadius: 'var(--radius-pill)', border: 'none',
      display: 'inline-flex', alignItems: 'center', justifyContent: 'center', cursor: disabled ? 'default' : 'pointer',
      opacity: disabled ? 0.4 : 1, transition: 'background var(--duration-fast) var(--ease-calm), transform var(--duration-fast) var(--ease-calm)',
      transform: isHover && !disabled ? 'scale(1.06)' : 'none',
      ...variants[variant], ...(isHover && !disabled && variant === 'ghost' ? { background: 'var(--surface-sunken)' } : {}),
    },
  }, React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: fs[size] } }, icon));
}
