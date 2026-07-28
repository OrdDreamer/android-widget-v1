import React from 'react';
export function InlineErrorBanner({ message, onDismiss }) {
  return React.createElement('div', {
    style: {
      display: 'flex', alignItems: 'center', gap: 10, background: '#F6E4E2', border: '1px solid #E3B9B4',
      color: 'var(--status-danger)', borderRadius: 'var(--radius-md)', padding: '12px 14px', fontFamily: 'var(--font-body)', fontSize: 'var(--fs-body-sm)',
    },
  },
    React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 18 } }, 'error'),
    React.createElement('span', { style: { flex: 1 } }, message),
    onDismiss && React.createElement('button', {
      type: 'button', onClick: onDismiss, 'aria-label': 'Dismiss',
      style: { background: 'none', border: 'none', cursor: 'pointer', color: 'inherit', display: 'flex' },
    }, React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 18 } }, 'close')));
}
