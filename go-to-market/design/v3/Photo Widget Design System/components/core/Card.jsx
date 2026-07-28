import React from 'react';
export function Card({ children, padding = 'md', variant = 'default', style }) {
  const paddings = { sm: 'var(--space-4)', md: 'var(--space-6)', lg: 'var(--space-8)' };
  const variants = {
    default: { background: 'var(--surface-card)', boxShadow: 'var(--shadow-sm)', border: '1px solid var(--border-subtle)' },
    raised: { background: 'var(--surface-raised)', boxShadow: 'var(--shadow-md)', border: 'none' },
    sunken: { background: 'var(--surface-sunken)', boxShadow: 'none', border: '1px solid var(--border-subtle)' },
  };
  return React.createElement('div', {
    style: { borderRadius: 'var(--radius-lg)', padding: paddings[padding], ...variants[variant], ...style },
  }, children);
}
