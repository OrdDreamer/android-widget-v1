import React from 'react';
export function Dialog({ open, title, children, onClose, actions }) {
  if (!open) return null;
  return React.createElement('div', {
    style: {
      position: 'fixed', inset: 0, background: 'rgba(33,31,27,0.35)', display: 'flex', alignItems: 'center',
      justifyContent: 'center', zIndex: 100, backdropFilter: 'blur(2px)',
    }, onClick: onClose,
  }, React.createElement('div', {
    onClick: e => e.stopPropagation(),
    style: {
      background: 'var(--surface-raised)', borderRadius: 'var(--radius-lg)', boxShadow: 'var(--shadow-lg)',
      padding: 'var(--space-6)', width: 360, maxWidth: '90vw', fontFamily: 'var(--font-body)',
    },
  },
    title && React.createElement('div', { style: { fontFamily: 'var(--font-display)', fontSize: 'var(--fs-title)', color: 'var(--text-primary)', marginBottom: 'var(--space-3)' } }, title),
    React.createElement('div', { style: { fontSize: 'var(--fs-body)', color: 'var(--text-secondary)', marginBottom: 'var(--space-6)' } }, children),
    actions && React.createElement('div', { style: { display: 'flex', justifyContent: 'flex-end', gap: 'var(--space-3)' } }, actions)));
}
