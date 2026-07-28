import React from 'react';
export function ProgressIndicator({ value, indeterminate }) {
  return React.createElement('div', {
    style: { width: '100%', height: 6, borderRadius: 'var(--radius-pill)', background: 'var(--surface-sunken)', overflow: 'hidden', position: 'relative' },
  }, React.createElement('div', {
    style: {
      position: 'absolute', top: 0, bottom: 0, left: 0, borderRadius: 'var(--radius-pill)', background: 'var(--accent-primary)',
      width: indeterminate ? '40%' : `${Math.max(0, Math.min(100, value))}%`,
      animation: indeterminate ? 'pw-indeterminate 1.2s var(--ease-calm) infinite' : 'none',
      transition: indeterminate ? 'none' : 'width var(--duration-base) var(--ease-calm)',
    },
  }), indeterminate && React.createElement('style', null, '@keyframes pw-indeterminate{0%{left:-40%}100%{left:100%}}'));
}
