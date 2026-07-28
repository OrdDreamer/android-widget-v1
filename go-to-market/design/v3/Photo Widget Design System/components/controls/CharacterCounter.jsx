import React from 'react';
export function CharacterCounter({ current, max }) {
  const over = current > max;
  const near = !over && current >= max * 0.9;
  return React.createElement('span', {
    style: {
      fontFamily: 'var(--font-body)', fontSize: 'var(--fs-caption)',
      color: over ? 'var(--status-danger)' : (near ? 'var(--status-warning)' : 'var(--text-muted)'),
    },
  }, `${current}/${max}`);
}
