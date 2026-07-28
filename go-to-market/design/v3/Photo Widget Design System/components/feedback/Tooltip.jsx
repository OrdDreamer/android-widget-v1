import React from 'react';
export function Tooltip({ children, label, side = 'top' }) {
  const [show, setShow] = React.useState(false);
  const pos = {
    top: { bottom: '100%', left: '50%', transform: 'translateX(-50%)', marginBottom: 8 },
    bottom: { top: '100%', left: '50%', transform: 'translateX(-50%)', marginTop: 8 },
  };
  return React.createElement('span', {
    style: { position: 'relative', display: 'inline-flex' },
    onMouseEnter: () => setShow(true), onMouseLeave: () => setShow(false),
  }, children, React.createElement('span', {
    style: {
      position: 'absolute', ...pos[side], background: 'var(--surface-inverse)', color: 'var(--text-inverse)',
      fontFamily: 'var(--font-body)', fontSize: 'var(--fs-caption)', padding: '6px 10px', borderRadius: 'var(--radius-sm)',
      whiteSpace: 'nowrap', pointerEvents: 'none', boxShadow: 'var(--shadow-md)',
      opacity: show ? 1 : 0, transition: 'opacity var(--duration-fast) var(--ease-calm)',
    },
  }, label));
}
