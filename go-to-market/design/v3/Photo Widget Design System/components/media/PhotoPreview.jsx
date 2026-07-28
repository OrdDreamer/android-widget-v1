import React from 'react';
export function PhotoPreview({ state = 'empty', src, alt = '', onSelect, onRetry, size = 200 }) {
  const base = {
    width: size, height: size, borderRadius: 'var(--radius-lg)', overflow: 'hidden', position: 'relative',
    display: 'flex', alignItems: 'center', justifyContent: 'center', fontFamily: 'var(--font-body)',
  };
  if (state === 'loaded') {
    return React.createElement('div', { style: { ...base, background: 'var(--surface-sunken)' } },
      src ? React.createElement('img', { src, alt, style: { width: '100%', height: '100%', objectFit: 'cover' } })
        : React.createElement('div', { style: { width: '100%', height: '100%', background: 'linear-gradient(135deg,#caa27a,#8a6a52)' } }),
      onSelect && React.createElement('button', {
        type: 'button', onClick: onSelect, 'aria-label': 'Change photo',
        style: {
          position: 'absolute', bottom: 10, right: 10, width: 36, height: 36, borderRadius: '50%', border: 'none',
          background: 'rgba(33,31,27,0.55)', color: '#fff', cursor: 'pointer', display: 'flex', alignItems: 'center', justifyContent: 'center',
          backdropFilter: 'blur(4px)',
        },
      }, React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 18 } }, 'edit')));
  }
  if (state === 'error') {
    return React.createElement('div', {
      style: { ...base, background: '#F6E4E2', border: '1px solid #E3B9B4', flexDirection: 'column', gap: 10, cursor: onRetry ? 'pointer' : 'default' },
      onClick: onRetry,
    },
      React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 32, color: 'var(--status-danger)' } }, 'broken_image'),
      React.createElement('span', { style: { fontSize: 'var(--fs-body-sm)', color: 'var(--status-danger)', textAlign: 'center', padding: '0 16px' } }, "Couldn't load this photo"),
      onRetry && React.createElement('span', { style: { fontSize: 'var(--fs-caption)', color: 'var(--status-danger)', fontWeight: 'var(--fw-semibold)', textDecoration: 'underline' } }, 'Try again'));
  }
  return React.createElement('div', {
    style: { ...base, background: 'var(--surface-sunken)', border: '1.5px dashed var(--border-default)', flexDirection: 'column', gap: 10, cursor: onSelect ? 'pointer' : 'default' },
    onClick: onSelect,
  },
    React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 32, color: 'var(--text-muted)' } }, 'add_photo_alternate'),
    React.createElement('span', { style: { fontSize: 'var(--fs-body-sm)', color: 'var(--text-muted)', textAlign: 'center', padding: '0 16px' } }, 'No photo yet'));
}
