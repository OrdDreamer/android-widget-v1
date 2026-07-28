function HomeScreen({ onCustomize }) {
  const { IconButton } = window.PhotoWidgetDesignSystem_69a50d;
  return React.createElement('div', {
    style: {
      width: '100%', height: '100%', backgroundImage: 'linear-gradient(180deg,#3a4a5c 0%,#1c2530 100%)',
      display: 'flex', flexDirection: 'column', position: 'relative',
    },
  },
    React.createElement(window.StatusBar, null),
    React.createElement('div', { style: { flex: 1, display: 'flex', alignItems: 'center', justifyContent: 'center', padding: 28 } },
      React.createElement('div', {
        onClick: onCustomize,
        style: {
          width: 210, height: 210, borderRadius: 'var(--radius-lg)', overflow: 'hidden', boxShadow: '0 18px 40px rgba(0,0,0,0.4)',
          cursor: 'pointer', position: 'relative', background: 'linear-gradient(135deg,#caa27a,#8a6a52)',
        },
      },
        React.createElement('div', { style: { position: 'absolute', inset: 0, display: 'flex', alignItems: 'flex-end', padding: 14 } },
          React.createElement('div', { style: { fontFamily: 'var(--font-display)', fontStyle: 'italic', color: '#fff', fontSize: 15, textShadow: '0 1px 6px rgba(0,0,0,0.4)' } }, 'Sunday, at the lake')))),
    React.createElement('div', { style: { display: 'flex', justifyContent: 'center', gap: 14, paddingBottom: 30 } },
      ['message', 'camera_alt', 'photo_camera', 'call'].map((ic, i) =>
        React.createElement(IconButton, { key: i, icon: ic, variant: 'filled', label: ic })))
  );
}
window.HomeScreen = HomeScreen;
