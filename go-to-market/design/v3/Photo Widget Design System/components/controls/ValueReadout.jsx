import React from 'react';
export function ValueReadout({ label, value, tone = 'neutral' }) {
  const tones = {
    neutral: { background: 'var(--surface-sunken)', color: 'var(--text-primary)' },
    accent: { background: 'var(--accent-primary-subtle)', color: 'var(--pw-terracotta-700)' },
  };
  return React.createElement('div', {
    style: { display: 'inline-flex', flexDirection: 'column', alignItems: 'center', gap: 2, padding: '8px 16px', borderRadius: 'var(--radius-md)', fontFamily: 'var(--font-body)', ...tones[tone] },
  },
    React.createElement('span', { style: { fontSize: 'var(--fs-title)', fontWeight: 'var(--fw-semibold)' } }, value),
    label && React.createElement('span', { style: { fontSize: 'var(--fs-caption)', color: 'var(--text-muted)', textTransform: 'uppercase', letterSpacing: 'var(--tracking-wide)' } }, label));
}
