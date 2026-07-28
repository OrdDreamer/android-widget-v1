import React from 'react';
export function Tag({ children, selected, onClick }) {
  const [isHover, setHover] = React.useState(false);
  return React.createElement('button', {
    type: 'button', onClick,
    onMouseEnter: () => setHover(true), onMouseLeave: () => setHover(false),
    style: {
      fontFamily: 'var(--font-body)', fontSize: 'var(--fs-body-sm)', fontWeight: 'var(--fw-medium)',
      padding: '7px 16px', borderRadius: 'var(--radius-pill)', cursor: 'pointer',
      border: selected ? '1px solid var(--accent-primary)' : '1px solid var(--border-default)',
      background: selected ? 'var(--accent-primary-subtle)' : (isHover ? 'var(--surface-sunken)' : 'transparent'),
      color: selected ? 'var(--pw-terracotta-700)' : 'var(--text-secondary)',
      transition: 'background var(--duration-fast) var(--ease-calm), border-color var(--duration-fast) var(--ease-calm)',
    },
  }, children);
}
