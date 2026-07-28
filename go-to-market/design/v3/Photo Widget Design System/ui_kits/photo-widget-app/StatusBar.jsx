function StatusBar({ dark }) {
  const color = dark ? 'var(--text-primary)' : '#fff';
  return React.createElement('div', {
    style: { display: 'flex', justifyContent: 'space-between', padding: '14px 22px 4px', fontFamily: 'var(--font-body)', fontSize: 13, fontWeight: 600, color },
  }, React.createElement('span', null, '9:41'), React.createElement('span', { style: { display: 'flex', gap: 6 } },
    React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 16 } }, 'signal_cellular_alt'),
    React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 16 } }, 'wifi'),
    React.createElement('span', { className: 'material-symbols-rounded', style: { fontSize: 16 } }, 'battery_full')));
}
window.StatusBar = StatusBar;
