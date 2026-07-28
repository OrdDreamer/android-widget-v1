function OnboardingScreen({ onNext }) {
  const { Button } = window.PhotoWidgetDesignSystem_69a50d;
  return React.createElement('div', { style: { width: '100%', height: '100%', background: 'var(--pw-terracotta-500)', display: 'flex', flexDirection: 'column' } },
    React.createElement(window.StatusBar, null),
    React.createElement('div', { style: { flex: 1, display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', padding: 32, textAlign: 'center', gap: 16 } },
      React.createElement('div', { style: { fontFamily: 'var(--font-display)', fontStyle: 'italic', fontWeight: 500, fontSize: 40, color: 'var(--pw-cream-50)' } }, 'Photo Widget'),
      React.createElement('div', { style: { fontFamily: 'var(--font-body)', fontSize: 16, color: 'var(--pw-cream-100)', lineHeight: 1.55, maxWidth: 260 } }, 'Pick a photo. Resize it. Let it sit there.')),
    React.createElement('div', { style: { padding: '0 24px 40px' } },
      React.createElement(Button, { variant: 'ghost', size: 'lg', onClick: onNext, style: { width: '100%', background: 'var(--pw-cream-50)', color: 'var(--pw-terracotta-700)' } }, 'Get started')));
}
window.OnboardingScreen = OnboardingScreen;
