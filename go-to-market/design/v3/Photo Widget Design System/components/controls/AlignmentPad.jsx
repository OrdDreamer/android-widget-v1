import React from 'react';
const CELLS = [
  { value: 'top-left', icon: 'north_west' }, { value: 'top', icon: 'north' }, { value: 'top-right', icon: 'north_east' },
  { value: 'left', icon: 'west' }, { value: 'center', icon: 'radio_button_checked' }, { value: 'right', icon: 'east' },
  { value: 'bottom-left', icon: 'south_west' }, { value: 'bottom', icon: 'south' }, { value: 'bottom-right', icon: 'south_east' },
];
export function AlignmentPad({ value = 'center', onChange }) {
  const [hovered, setHovered] = React.useState(null);
  return React.createElement('div', {
    style: { display: 'grid', gridTemplateColumns: 'repeat(3,36px)', gridTemplateRows: 'repeat(3,36px)', gap: 4, padding: 8, background: 'var(--surface-sunken)', borderRadius: 'var(--radius-md)', width: 'fit-content' },
  }, CELLS.map(c => {
    const selected = c.value === value;
    const isHover = c.value === hovered;
    return React.createElement('button', {
      key: c.value, type: 'button', onClick: () => onChange && onChange(c.value),
      onMouseEnter: () => setHovered(c.value), onMouseLeave: () => setHovered(null),
      'aria-label': c.value, 'aria-pressed': selected,
      style: {
        width: 36, height: 36, borderRadius: 8, border: 'none', cursor: 'pointer',
        background: selected ? 'var(--accent-primary)' : (isHover ? 'var(--surface-raised)' : 'transparent'),
        display: 'flex', alignItems: 'center', justifyContent: 'center',
        transition: 'background var(--duration-fast) var(--ease-calm)',
      },
    }, React.createElement('span', {
      className: 'material-symbols-rounded',
      style: { fontSize: c.value === 'center' ? 10 : 18, color: selected ? 'var(--text-on-accent)' : 'var(--text-muted)' },
    }, c.icon));
  }));
}
