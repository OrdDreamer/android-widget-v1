import React from 'react';
export function Toast({ message, tone = 'default', icon, visible = true }) {
  const tones = {
    default: { background: 'var(--surface-inverse)', color: 'var(--text-inverse)' },
    success: { background: 'var(--pw-sage-700)', color: 'var(--text-inverse)' },
    danger: { background: 'var(--status-danger)', color: 'var(--text-inverse)' },
  };
  return React.createElement('div', {
    style: {
      display: 'inline-flex', alignItems: 'center', gap: 10, fontFamily: 'var(--font-body)', fontSize: 'var(--fs-body-sm)',
      padding: '12px 18px', borderRadius: 'var(--radius-md)', boxShadow: 'var(--shadow-lg)', ...tones[tone],
      opacity: visible ? 1 : 0, transform: visible ? 'translateY(0)' : 'translateY(8px)',
      transition: 'opacity var(--duration-base) var(--ease-calm), transform var(--duration-base) var(--ease-calm)',
    },
  }, icon && React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 18 } }, icon), message);
}
