function PhoneFrame({ children }) {
  return React.createElement('div', {
    style: { width: 360, height: 740, borderRadius: 44, background: '#0c0c0c', padding: 10, boxShadow: '0 24px 60px rgba(33,31,27,0.35)', position: 'relative', flexShrink: 0 },
  }, React.createElement('div', { style: { width: '100%', height: '100%', borderRadius: 34, overflow: 'hidden', position: 'relative', background: '#000' } }, children));
}
window.PhoneFrame = PhoneFrame;
