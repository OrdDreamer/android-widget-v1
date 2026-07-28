import React from 'react';
export function Button({ children, variant = 'primary', size = 'md', icon, disabled, onClick, type = 'button' }) {
  const base = {
    display: 'inline-flex', alignItems: 'center', justifyContent: 'center', gap: 'var(--space-2)',
    fontFamily: 'var(--font-body)', fontWeight: 'var(--fw-semibold)', border: '1px solid transparent',
    borderRadius: 'var(--radius-md)', cursor: disabled ? 'default' : 'pointer',
    transition: `background var(--duration-fast) var(--ease-calm), color var(--duration-fast) var(--ease-calm), border-color var(--duration-fast) var(--ease-calm), transform var(--duration-fast) var(--ease-calm)`,
    opacity: disabled ? 0.45 : 1, pointerEvents: disabled ? 'none' : 'auto',
  };
  const sizes = {
    sm: { padding: '6px 14px', fontSize: 'var(--fs-body-sm)' },
    md: { padding: '10px 20px', fontSize: 'var(--fs-body)' },
    lg: { padding: '13px 26px', fontSize: 'var(--fs-body-lg)' },
  };
  const variants = {
    primary: { background: 'var(--accent-primary)', color: 'var(--text-on-accent)' },
    secondary: { background: 'var(--pw-sage-500)', color: 'var(--text-on-accent)' },
    outline: { background: 'transparent', color: 'var(--text-primary)', borderColor: 'var(--border-default)' },
    ghost: { background: 'transparent', color: 'var(--text-primary)' },
  };
  const hover = {
    primary: { background: 'var(--accent-primary-hover)' },
    secondary: { background: 'var(--accent-secondary-hover)' },
    outline: { background: 'var(--surface-sunken)' },
    ghost: { background: 'var(--surface-sunken)' },
  };
  const [isHover, setHover] = React.useState(false);
  const [isActive, setActive] = React.useState(false);
  const style = { ...base, ...sizes[size], ...variants[variant], ...(isHover && !disabled ? hover[variant] : {}), transform: isActive && !disabled ? 'scale(0.97)' : 'none' };
  return React.createElement('button', {
    type, disabled, onClick, style,
    onMouseEnter: () => setHover(true), onMouseLeave: () => { setHover(false); setActive(false); },
    onMouseDown: () => setActive(true), onMouseUp: () => setActive(false),
  }, icon ? React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: '1.15em', lineHeight: 1 } }, icon) : null, children);
}
