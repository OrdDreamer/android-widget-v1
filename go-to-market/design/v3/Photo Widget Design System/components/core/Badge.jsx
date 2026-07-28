import React from 'react';
export function Badge({ children, tone = 'neutral' }) {
  const tones = {
    neutral: { background: 'var(--surface-sunken)', color: 'var(--text-secondary)' },
    accent: { background: 'var(--accent-primary-subtle)', color: 'var(--pw-terracotta-700)' },
    success: { background: 'var(--pw-sage-100)', color: 'var(--pw-sage-700)' },
    danger: { background: '#F2DEDC', color: 'var(--status-danger)' },
  };
  return React.createElement('span', {
    style: {
      display: 'inline-flex', alignItems: 'center', fontFamily: 'var(--font-body)', fontWeight: 'var(--fw-semibold)',
      fontSize: 'var(--fs-caption)', letterSpacing: 'var(--tracking-wide)', textTransform: 'uppercase',
      padding: '4px 10px', borderRadius: 'var(--radius-pill)', ...tones[tone],
    },
  }, children);
}
