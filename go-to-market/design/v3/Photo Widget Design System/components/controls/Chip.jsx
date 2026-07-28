import React from 'react';
export function Chip({ children, icon, selected, onClick }) {
  const [isHover, setHover] = React.useState(false);
  return React.createElement('button', {
    type: 'button', onClick,
    onMouseEnter: () => setHover(true), onMouseLeave: () => setHover(false),
    style: {
      display: 'inline-flex', alignItems: 'center', gap: 6, fontFamily: 'var(--font-body)', fontSize: 'var(--fs-body-sm)',
      fontWeight: 'var(--fw-medium)', padding: '7px 14px', borderRadius: 'var(--radius-pill)', cursor: 'pointer',
      border: selected ? '1px solid var(--accent-primary)' : '1px solid var(--border-subtle)',
      background: selected ? 'var(--accent-primary)' : (isHover ? 'var(--surface-sunken)' : 'var(--surface-card)'),
      color: selected ? 'var(--text-on-accent)' : 'var(--text-secondary)',
      transition: 'background var(--duration-fast) var(--ease-calm), border-color var(--duration-fast) var(--ease-calm)',
    },
  }, icon && React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 16 } }, icon), children);
}
