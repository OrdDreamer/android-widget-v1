import React from 'react';
export function HelperNote({ children, icon }) {
  return React.createElement('div', {
    style: { display: 'flex', alignItems: 'flex-start', gap: 6, fontFamily: 'var(--font-body)', fontSize: 'var(--fs-caption)', color: 'var(--text-muted)' },
  }, icon && React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 14, marginTop: 1 } }, icon),
    React.createElement('span', null, children));
}
