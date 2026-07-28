function CustomizeScreen({ onBack }) {
  const { IconButton, Tabs, Radio, Switch, Input, Button, Card } = window.PhotoWidgetDesignSystem_69a50d;
  const [tab, setTab] = React.useState('frame');
  const [shape, setShape] = React.useState('rounded');
  const [caption, setCaption] = React.useState('Sunday, at the lake');
  const [showCaption, setShowCaption] = React.useState(true);
  const [autoRefresh, setAutoRefresh] = React.useState(false);
  return React.createElement('div', { style: { width: '100%', height: '100%', background: 'var(--surface-app)', display: 'flex', flexDirection: 'column' } },
    React.createElement(window.StatusBar, { dark: true }),
    React.createElement('div', { style: { display: 'flex', alignItems: 'center', gap: 8, padding: '4px 12px' } },
      React.createElement(IconButton, { icon: 'arrow_back', label: 'Back', onClick: onBack }),
      React.createElement('div', { style: { fontFamily: 'var(--font-display)', fontSize: 20, color: 'var(--text-primary)' } }, 'Customize widget')),
    React.createElement('div', { style: { padding: '8px 20px' } },
      React.createElement('div', {
        style: { width: 140, height: 140, margin: '0 auto 20px', borderRadius: shape === 'rounded' ? 'var(--radius-lg)' : (shape === 'round' ? '50%' : '0px'), overflow: 'hidden', boxShadow: 'var(--shadow-md)', background: 'linear-gradient(135deg,#caa27a,#8a6a52)' },
      }),
      React.createElement(Tabs, { tabs: [{ label: 'Frame', value: 'frame' }, { label: 'Caption', value: 'caption' }, { label: 'Behavior', value: 'behavior' }], value: tab, onChange: setTab })),
    React.createElement('div', { style: { flex: 1, padding: '20px', display: 'flex', flexDirection: 'column', gap: 16, overflow: 'auto' } },
      tab === 'frame' && React.createElement(Card, { padding: 'sm' },
        React.createElement('div', { style: { display: 'flex', flexDirection: 'column', gap: 12 } },
          React.createElement(Radio, { name: 'shape', label: 'Rounded', checked: shape === 'rounded', onChange: () => setShape('rounded') }),
          React.createElement(Radio, { name: 'shape', label: 'Square', checked: shape === 'square', onChange: () => setShape('square') }),
          React.createElement(Radio, { name: 'shape', label: 'Round', checked: shape === 'round', onChange: () => setShape('round') }))),
      tab === 'caption' && React.createElement('div', { style: { display: 'flex', flexDirection: 'column', gap: 16 } },
        React.createElement(Switch, { label: 'Show caption', checked: showCaption, onChange: setShowCaption }),
        showCaption && React.createElement(Input, { label: 'Caption', value: caption, onChange: setCaption, icon: 'edit' })),
      tab === 'behavior' && React.createElement('div', { style: { display: 'flex', flexDirection: 'column', gap: 16 } },
        React.createElement(Switch, { label: 'Auto-refresh from album', checked: autoRefresh, onChange: setAutoRefresh }))),
    React.createElement('div', { style: { padding: '0 20px 28px' } },
      React.createElement(Button, { variant: 'primary', size: 'lg', style: { width: '100%' } }, 'Done')));
}
window.CustomizeScreen = CustomizeScreen;
