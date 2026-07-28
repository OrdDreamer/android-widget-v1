/* @ds-bundle: {"format":4,"namespace":"PhotoWidgetDesignSystem_69a50d","components":[{"name":"AlignmentPad","sourcePath":"components/controls/AlignmentPad.jsx"},{"name":"CharacterCounter","sourcePath":"components/controls/CharacterCounter.jsx"},{"name":"Chip","sourcePath":"components/controls/Chip.jsx"},{"name":"FrameStyleSelector","sourcePath":"components/controls/FrameStyleSelector.jsx"},{"name":"RotationControl","sourcePath":"components/controls/RotationControl.jsx"},{"name":"SegmentedControl","sourcePath":"components/controls/SegmentedControl.jsx"},{"name":"Slider","sourcePath":"components/controls/Slider.jsx"},{"name":"SliderPresets","sourcePath":"components/controls/SliderPresets.jsx"},{"name":"ValueReadout","sourcePath":"components/controls/ValueReadout.jsx"},{"name":"Badge","sourcePath":"components/core/Badge.jsx"},{"name":"Button","sourcePath":"components/core/Button.jsx"},{"name":"Card","sourcePath":"components/core/Card.jsx"},{"name":"IconButton","sourcePath":"components/core/IconButton.jsx"},{"name":"Tag","sourcePath":"components/core/Tag.jsx"},{"name":"HelperNote","sourcePath":"components/feedback/HelperNote.jsx"},{"name":"InlineErrorBanner","sourcePath":"components/feedback/InlineErrorBanner.jsx"},{"name":"ProgressIndicator","sourcePath":"components/feedback/ProgressIndicator.jsx"},{"name":"Toast","sourcePath":"components/feedback/Toast.jsx"},{"name":"Tooltip","sourcePath":"components/feedback/Tooltip.jsx"},{"name":"Checkbox","sourcePath":"components/forms/Checkbox.jsx"},{"name":"Input","sourcePath":"components/forms/Input.jsx"},{"name":"Radio","sourcePath":"components/forms/Radio.jsx"},{"name":"Select","sourcePath":"components/forms/Select.jsx"},{"name":"Switch","sourcePath":"components/forms/Switch.jsx"},{"name":"PhotoPreview","sourcePath":"components/media/PhotoPreview.jsx"},{"name":"Tabs","sourcePath":"components/navigation/Tabs.jsx"},{"name":"Dialog","sourcePath":"components/overlay/Dialog.jsx"}],"sourceHashes":{"components/controls/AlignmentPad.jsx":"c753b3dc5af3","components/controls/CharacterCounter.jsx":"f4941b5d1b05","components/controls/Chip.jsx":"2f4580f49728","components/controls/FrameStyleSelector.jsx":"64dd52066d2a","components/controls/RotationControl.jsx":"279f479e8e72","components/controls/SegmentedControl.jsx":"8ae3f5fef447","components/controls/Slider.jsx":"ac4371ebab75","components/controls/SliderPresets.jsx":"819085706da8","components/controls/ValueReadout.jsx":"cf5fb65923a2","components/core/Badge.jsx":"59532eb4c6c0","components/core/Button.jsx":"928477489433","components/core/Card.jsx":"6f433fade0c4","components/core/IconButton.jsx":"7d9c7f694057","components/core/Tag.jsx":"5fea85d8119c","components/feedback/HelperNote.jsx":"f49dbd3e84ae","components/feedback/InlineErrorBanner.jsx":"5ac1ec035d4a","components/feedback/ProgressIndicator.jsx":"ac6d0e7e80a2","components/feedback/Toast.jsx":"6e8e87006166","components/feedback/Tooltip.jsx":"758de1303af7","components/forms/Checkbox.jsx":"e66b55fb95e3","components/forms/Input.jsx":"76f631827612","components/forms/Radio.jsx":"697a5ef31089","components/forms/Select.jsx":"9f8a7715e8a8","components/forms/Switch.jsx":"95e67462639f","components/media/PhotoPreview.jsx":"a18b8841d2d7","components/navigation/Tabs.jsx":"043988f9fc60","components/overlay/Dialog.jsx":"dae9edbfe16a","ui_kits/photo-widget-app/CustomizeScreen.jsx":"5edd8f4be6c0","ui_kits/photo-widget-app/HomeScreen.jsx":"6ddcd2cfee2c","ui_kits/photo-widget-app/OnboardingScreen.jsx":"5250af81268b","ui_kits/photo-widget-app/PhoneFrame.jsx":"38f254123c11","ui_kits/photo-widget-app/StatusBar.jsx":"a0cc749ac4bb"},"inlinedExternals":[],"unexposedExports":[]} */

(() => {

const __ds_ns = (window.PhotoWidgetDesignSystem_69a50d = window.PhotoWidgetDesignSystem_69a50d || {});

const __ds_scope = {};

(__ds_ns.__errors = __ds_ns.__errors || []);

// components/controls/AlignmentPad.jsx
try { (() => {
const CELLS = [{
  value: 'top-left',
  icon: 'north_west'
}, {
  value: 'top',
  icon: 'north'
}, {
  value: 'top-right',
  icon: 'north_east'
}, {
  value: 'left',
  icon: 'west'
}, {
  value: 'center',
  icon: 'radio_button_checked'
}, {
  value: 'right',
  icon: 'east'
}, {
  value: 'bottom-left',
  icon: 'south_west'
}, {
  value: 'bottom',
  icon: 'south'
}, {
  value: 'bottom-right',
  icon: 'south_east'
}];
function AlignmentPad({
  value = 'center',
  onChange
}) {
  const [hovered, setHovered] = React.useState(null);
  return React.createElement('div', {
    style: {
      display: 'grid',
      gridTemplateColumns: 'repeat(3,36px)',
      gridTemplateRows: 'repeat(3,36px)',
      gap: 4,
      padding: 8,
      background: 'var(--surface-sunken)',
      borderRadius: 'var(--radius-md)',
      width: 'fit-content'
    }
  }, CELLS.map(c => {
    const selected = c.value === value;
    const isHover = c.value === hovered;
    return React.createElement('button', {
      key: c.value,
      type: 'button',
      onClick: () => onChange && onChange(c.value),
      onMouseEnter: () => setHovered(c.value),
      onMouseLeave: () => setHovered(null),
      'aria-label': c.value,
      'aria-pressed': selected,
      style: {
        width: 36,
        height: 36,
        borderRadius: 8,
        border: 'none',
        cursor: 'pointer',
        background: selected ? 'var(--accent-primary)' : isHover ? 'var(--surface-raised)' : 'transparent',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        transition: 'background var(--duration-fast) var(--ease-calm)'
      }
    }, React.createElement('span', {
      className: 'material-symbols-rounded',
      style: {
        fontSize: c.value === 'center' ? 10 : 18,
        color: selected ? 'var(--text-on-accent)' : 'var(--text-muted)'
      }
    }, c.icon));
  }));
}
Object.assign(__ds_scope, { AlignmentPad });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/controls/AlignmentPad.jsx", error: String((e && e.message) || e) }); }

// components/controls/CharacterCounter.jsx
try { (() => {
function CharacterCounter({
  current,
  max
}) {
  const over = current > max;
  const near = !over && current >= max * 0.9;
  return React.createElement('span', {
    style: {
      fontFamily: 'var(--font-body)',
      fontSize: 'var(--fs-caption)',
      color: over ? 'var(--status-danger)' : near ? 'var(--status-warning)' : 'var(--text-muted)'
    }
  }, `${current}/${max}`);
}
Object.assign(__ds_scope, { CharacterCounter });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/controls/CharacterCounter.jsx", error: String((e && e.message) || e) }); }

// components/controls/Chip.jsx
try { (() => {
function Chip({
  children,
  icon,
  selected,
  onClick
}) {
  const [isHover, setHover] = React.useState(false);
  return React.createElement('button', {
    type: 'button',
    onClick,
    onMouseEnter: () => setHover(true),
    onMouseLeave: () => setHover(false),
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 6,
      fontFamily: 'var(--font-body)',
      fontSize: 'var(--fs-body-sm)',
      fontWeight: 'var(--fw-medium)',
      padding: '7px 14px',
      borderRadius: 'var(--radius-pill)',
      cursor: 'pointer',
      border: selected ? '1px solid var(--accent-primary)' : '1px solid var(--border-subtle)',
      background: selected ? 'var(--accent-primary)' : isHover ? 'var(--surface-sunken)' : 'var(--surface-card)',
      color: selected ? 'var(--text-on-accent)' : 'var(--text-secondary)',
      transition: 'background var(--duration-fast) var(--ease-calm), border-color var(--duration-fast) var(--ease-calm)'
    }
  }, icon && React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 16
    }
  }, icon), children);
}
Object.assign(__ds_scope, { Chip });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/controls/Chip.jsx", error: String((e && e.message) || e) }); }

// components/controls/FrameStyleSelector.jsx
try { (() => {
const SHAPES = [{
  value: 'none',
  label: 'None',
  radius: '0px'
}, {
  value: 'rounded',
  label: 'Rounded',
  radius: 'var(--radius-lg)'
}, {
  value: 'circle',
  label: 'Circle',
  radius: '50%'
}, {
  value: 'polaroid',
  label: 'Polaroid',
  radius: '2px'
}];
function FrameStyleSelector({
  value,
  onChange
}) {
  return React.createElement('div', {
    style: {
      display: 'flex',
      gap: 14
    }
  }, SHAPES.map(s => {
    const selected = s.value === value;
    return React.createElement('button', {
      key: s.value,
      type: 'button',
      onClick: () => onChange && onChange(s.value),
      style: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        gap: 8,
        background: 'none',
        border: 'none',
        cursor: 'pointer',
        fontFamily: 'var(--font-body)'
      }
    }, React.createElement('div', {
      style: {
        width: 56,
        height: 56,
        padding: s.value === 'polaroid' ? 6 : 0,
        boxSizing: 'border-box',
        background: s.value === 'polaroid' ? '#fff' : 'transparent',
        borderRadius: s.value === 'polaroid' ? 4 : 0,
        boxShadow: selected ? '0 0 0 2px var(--accent-primary), var(--shadow-sm)' : 'var(--shadow-sm)'
      }
    }, React.createElement('div', {
      style: {
        width: '100%',
        height: '100%',
        borderRadius: s.radius,
        background: 'linear-gradient(135deg,#caa27a,#8a6a52)'
      }
    })), React.createElement('span', {
      style: {
        fontSize: 'var(--fs-caption)',
        color: selected ? 'var(--text-primary)' : 'var(--text-muted)',
        fontWeight: selected ? 'var(--fw-semibold)' : 'var(--fw-regular)'
      }
    }, s.label));
  }));
}
Object.assign(__ds_scope, { FrameStyleSelector });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/controls/FrameStyleSelector.jsx", error: String((e && e.message) || e) }); }

// components/controls/RotationControl.jsx
try { (() => {
const STEP = 45;
function normalize(deg) {
  return (deg % 360 + 360) % 360;
}
function snap(deg) {
  return normalize(Math.round(normalize(deg) / STEP) * STEP);
}
function RotationControl({
  value = 0,
  onChange
}) {
  const dialRef = React.useRef(null);
  const setFromClientXY = (clientX, clientY) => {
    const rect = dialRef.current.getBoundingClientRect();
    const cx = rect.left + rect.width / 2,
      cy = rect.top + rect.height / 2;
    const deg = Math.atan2(clientX - cx, -(clientY - cy)) * (180 / Math.PI);
    onChange && onChange(snap(deg));
  };
  const onPointerDown = e => {
    setFromClientXY(e.clientX, e.clientY);
    const move = ev => setFromClientXY(ev.clientX, ev.clientY);
    const up = () => {
      window.removeEventListener('pointermove', move);
      window.removeEventListener('pointerup', up);
    };
    window.addEventListener('pointermove', move);
    window.addEventListener('pointerup', up);
  };
  const step = d => onChange && onChange(normalize(value + d));
  return React.createElement('div', {
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 16,
      fontFamily: 'var(--font-body)'
    }
  }, React.createElement('button', {
    type: 'button',
    onClick: () => step(-STEP),
    'aria-label': 'Rotate left 45°',
    style: {
      width: 32,
      height: 32,
      borderRadius: '50%',
      border: '1px solid var(--border-default)',
      background: 'var(--surface-card)',
      cursor: 'pointer',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    }
  }, React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 16
    }
  }, 'rotate_left')), React.createElement('div', {
    ref: dialRef,
    onPointerDown,
    style: {
      width: 64,
      height: 64,
      borderRadius: '50%',
      background: 'var(--surface-sunken)',
      position: 'relative',
      cursor: 'grab',
      touchAction: 'none'
    }
  }, [0, 45, 90, 135, 180, 225, 270, 315].map(tick => React.createElement('div', {
    key: tick,
    style: {
      position: 'absolute',
      top: '50%',
      left: '50%',
      width: 4,
      height: 4,
      borderRadius: '50%',
      background: tick === value ? 'var(--accent-primary)' : 'var(--border-strong)',
      transform: `rotate(${tick}deg) translate(0,-26px)`
    }
  })), React.createElement('div', {
    style: {
      position: 'absolute',
      top: '50%',
      left: '50%',
      width: 22,
      height: 4,
      borderRadius: 2,
      background: 'var(--accent-primary)',
      transformOrigin: 'left center',
      transform: `translate(0,-2px) rotate(${value - 90}deg)`
    }
  }), React.createElement('div', {
    style: {
      position: 'absolute',
      inset: 6,
      border: '1px solid var(--border-subtle)',
      borderRadius: '50%'
    }
  })), React.createElement('button', {
    type: 'button',
    onClick: () => step(STEP),
    'aria-label': 'Rotate right 45°',
    style: {
      width: 32,
      height: 32,
      borderRadius: '50%',
      border: '1px solid var(--border-default)',
      background: 'var(--surface-card)',
      cursor: 'pointer',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    }
  }, React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 16
    }
  }, 'rotate_right')), React.createElement('span', {
    style: {
      fontSize: 'var(--fs-body)',
      color: 'var(--text-primary)',
      fontWeight: 'var(--fw-semibold)',
      minWidth: 40
    }
  }, `${value}°`));
}
Object.assign(__ds_scope, { RotationControl });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/controls/RotationControl.jsx", error: String((e && e.message) || e) }); }

// components/controls/SegmentedControl.jsx
try { (() => {
function SegmentedControl({
  options = [],
  value,
  onChange,
  fullWidth
}) {
  const idx = Math.max(0, options.findIndex(o => o.value === value));
  return React.createElement('div', {
    style: {
      display: fullWidth ? 'flex' : 'inline-flex',
      position: 'relative',
      background: 'var(--surface-sunken)',
      borderRadius: 'var(--radius-pill)',
      padding: 4,
      gap: 2,
      width: fullWidth ? '100%' : 'auto'
    }
  }, React.createElement('div', {
    style: {
      position: 'absolute',
      top: 4,
      bottom: 4,
      left: `calc(${idx} * ${100 / options.length}% + 4px)`,
      width: `calc(${100 / options.length}% - 8px)`,
      background: 'var(--surface-raised)',
      borderRadius: 'var(--radius-pill)',
      boxShadow: 'var(--shadow-sm)',
      transition: 'left var(--duration-base) var(--ease-calm)'
    }
  }), options.map(o => React.createElement('button', {
    key: o.value,
    type: 'button',
    onClick: () => onChange && onChange(o.value),
    style: {
      position: 'relative',
      zIndex: 1,
      flex: fullWidth ? 1 : 'none',
      border: 'none',
      background: 'none',
      cursor: 'pointer',
      font: 'inherit',
      fontFamily: 'var(--font-body)',
      fontSize: 'var(--fs-body-sm)',
      fontWeight: o.value === value ? 'var(--fw-semibold)' : 'var(--fw-regular)',
      color: o.value === value ? 'var(--text-primary)' : 'var(--text-muted)',
      padding: '8px 16px',
      transition: 'color var(--duration-fast) var(--ease-calm)'
    }
  }, o.label)));
}
Object.assign(__ds_scope, { SegmentedControl });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/controls/SegmentedControl.jsx", error: String((e && e.message) || e) }); }

// components/controls/Slider.jsx
try { (() => {
function Slider({
  value = 0,
  min = 0,
  max = 100,
  onChange,
  label
}) {
  const trackRef = React.useRef(null);
  const pct = Math.max(0, Math.min(1, (value - min) / (max - min)));
  const setFromClientX = clientX => {
    const rect = trackRef.current.getBoundingClientRect();
    const ratio = Math.max(0, Math.min(1, (clientX - rect.left) / rect.width));
    onChange && onChange(Math.round((min + ratio * (max - min)) * 100) / 100);
  };
  const onPointerDown = e => {
    setFromClientX(e.clientX);
    const move = ev => setFromClientX(ev.clientX);
    const up = () => {
      window.removeEventListener('pointermove', move);
      window.removeEventListener('pointerup', up);
    };
    window.addEventListener('pointermove', move);
    window.addEventListener('pointerup', up);
  };
  return React.createElement('div', {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 8,
      fontFamily: 'var(--font-body)'
    }
  }, label && React.createElement('span', {
    style: {
      fontSize: 'var(--fs-body-sm)',
      fontWeight: 'var(--fw-medium)',
      color: 'var(--text-secondary)'
    }
  }, label), React.createElement('div', {
    ref: trackRef,
    onPointerDown,
    style: {
      position: 'relative',
      height: 24,
      display: 'flex',
      alignItems: 'center',
      cursor: 'pointer',
      touchAction: 'none'
    }
  }, React.createElement('div', {
    style: {
      position: 'absolute',
      left: 0,
      right: 0,
      height: 6,
      borderRadius: 'var(--radius-pill)',
      background: 'var(--border-subtle)'
    }
  }), React.createElement('div', {
    style: {
      position: 'absolute',
      left: 0,
      width: `${pct * 100}%`,
      height: 6,
      borderRadius: 'var(--radius-pill)',
      background: 'var(--accent-primary)'
    }
  }), React.createElement('div', {
    style: {
      position: 'absolute',
      left: `calc(${pct * 100}% - 10px)`,
      top: 2,
      width: 20,
      height: 20,
      borderRadius: '50%',
      background: '#fff',
      boxShadow: 'var(--shadow-md)',
      border: '1px solid var(--border-subtle)'
    }
  })));
}
Object.assign(__ds_scope, { Slider });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/controls/Slider.jsx", error: String((e && e.message) || e) }); }

// components/controls/SliderPresets.jsx
try { (() => {
function SliderPresets({
  label,
  value,
  min = 0,
  max = 100,
  presets = [],
  unit = '',
  onChange
}) {
  const Slider = window.PhotoWidgetDesignSystem_69a50d ? window.PhotoWidgetDesignSystem_69a50d.Slider : null;
  return React.createElement('div', {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 10,
      fontFamily: 'var(--font-body)'
    }
  }, Slider ? React.createElement(Slider, {
    label,
    value,
    min,
    max,
    onChange
  }) : React.createElement('span', {
    style: {
      fontSize: 'var(--fs-body-sm)',
      color: 'var(--text-secondary)'
    }
  }, label), React.createElement('div', {
    style: {
      display: 'flex',
      gap: 8
    }
  }, presets.map(p => {
    const selected = p === value;
    return React.createElement('button', {
      key: p,
      type: 'button',
      onClick: () => onChange && onChange(p),
      style: {
        flex: 1,
        padding: '6px 0',
        borderRadius: 'var(--radius-sm)',
        border: `1px solid ${selected ? 'var(--accent-primary)' : 'var(--border-subtle)'}`,
        background: selected ? 'var(--accent-primary-subtle)' : 'var(--surface-card)',
        color: selected ? 'var(--pw-terracotta-700)' : 'var(--text-muted)',
        fontSize: 'var(--fs-caption)',
        cursor: 'pointer',
        fontFamily: 'var(--font-body)'
      }
    }, `${p}${unit}`);
  })));
}
Object.assign(__ds_scope, { SliderPresets });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/controls/SliderPresets.jsx", error: String((e && e.message) || e) }); }

// components/controls/ValueReadout.jsx
try { (() => {
function ValueReadout({
  label,
  value,
  tone = 'neutral'
}) {
  const tones = {
    neutral: {
      background: 'var(--surface-sunken)',
      color: 'var(--text-primary)'
    },
    accent: {
      background: 'var(--accent-primary-subtle)',
      color: 'var(--pw-terracotta-700)'
    }
  };
  return React.createElement('div', {
    style: {
      display: 'inline-flex',
      flexDirection: 'column',
      alignItems: 'center',
      gap: 2,
      padding: '8px 16px',
      borderRadius: 'var(--radius-md)',
      fontFamily: 'var(--font-body)',
      ...tones[tone]
    }
  }, React.createElement('span', {
    style: {
      fontSize: 'var(--fs-title)',
      fontWeight: 'var(--fw-semibold)'
    }
  }, value), label && React.createElement('span', {
    style: {
      fontSize: 'var(--fs-caption)',
      color: 'var(--text-muted)',
      textTransform: 'uppercase',
      letterSpacing: 'var(--tracking-wide)'
    }
  }, label));
}
Object.assign(__ds_scope, { ValueReadout });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/controls/ValueReadout.jsx", error: String((e && e.message) || e) }); }

// components/core/Badge.jsx
try { (() => {
function Badge({
  children,
  tone = 'neutral'
}) {
  const tones = {
    neutral: {
      background: 'var(--surface-sunken)',
      color: 'var(--text-secondary)'
    },
    accent: {
      background: 'var(--accent-primary-subtle)',
      color: 'var(--pw-terracotta-700)'
    },
    success: {
      background: 'var(--pw-sage-100)',
      color: 'var(--pw-sage-700)'
    },
    danger: {
      background: '#F2DEDC',
      color: 'var(--status-danger)'
    }
  };
  return React.createElement('span', {
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      fontFamily: 'var(--font-body)',
      fontWeight: 'var(--fw-semibold)',
      fontSize: 'var(--fs-caption)',
      letterSpacing: 'var(--tracking-wide)',
      textTransform: 'uppercase',
      padding: '4px 10px',
      borderRadius: 'var(--radius-pill)',
      ...tones[tone]
    }
  }, children);
}
Object.assign(__ds_scope, { Badge });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/core/Badge.jsx", error: String((e && e.message) || e) }); }

// components/core/Button.jsx
try { (() => {
function Button({
  children,
  variant = 'primary',
  size = 'md',
  icon,
  disabled,
  onClick,
  type = 'button'
}) {
  const base = {
    display: 'inline-flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 'var(--space-2)',
    fontFamily: 'var(--font-body)',
    fontWeight: 'var(--fw-semibold)',
    border: '1px solid transparent',
    borderRadius: 'var(--radius-md)',
    cursor: disabled ? 'default' : 'pointer',
    transition: `background var(--duration-fast) var(--ease-calm), color var(--duration-fast) var(--ease-calm), border-color var(--duration-fast) var(--ease-calm), transform var(--duration-fast) var(--ease-calm)`,
    opacity: disabled ? 0.45 : 1,
    pointerEvents: disabled ? 'none' : 'auto'
  };
  const sizes = {
    sm: {
      padding: '6px 14px',
      fontSize: 'var(--fs-body-sm)'
    },
    md: {
      padding: '10px 20px',
      fontSize: 'var(--fs-body)'
    },
    lg: {
      padding: '13px 26px',
      fontSize: 'var(--fs-body-lg)'
    }
  };
  const variants = {
    primary: {
      background: 'var(--accent-primary)',
      color: 'var(--text-on-accent)'
    },
    secondary: {
      background: 'var(--pw-sage-500)',
      color: 'var(--text-on-accent)'
    },
    outline: {
      background: 'transparent',
      color: 'var(--text-primary)',
      borderColor: 'var(--border-default)'
    },
    ghost: {
      background: 'transparent',
      color: 'var(--text-primary)'
    }
  };
  const hover = {
    primary: {
      background: 'var(--accent-primary-hover)'
    },
    secondary: {
      background: 'var(--accent-secondary-hover)'
    },
    outline: {
      background: 'var(--surface-sunken)'
    },
    ghost: {
      background: 'var(--surface-sunken)'
    }
  };
  const [isHover, setHover] = React.useState(false);
  const [isActive, setActive] = React.useState(false);
  const style = {
    ...base,
    ...sizes[size],
    ...variants[variant],
    ...(isHover && !disabled ? hover[variant] : {}),
    transform: isActive && !disabled ? 'scale(0.97)' : 'none'
  };
  return React.createElement('button', {
    type,
    disabled,
    onClick,
    style,
    onMouseEnter: () => setHover(true),
    onMouseLeave: () => {
      setHover(false);
      setActive(false);
    },
    onMouseDown: () => setActive(true),
    onMouseUp: () => setActive(false)
  }, icon ? React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: '1.15em',
      lineHeight: 1
    }
  }, icon) : null, children);
}
Object.assign(__ds_scope, { Button });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/core/Button.jsx", error: String((e && e.message) || e) }); }

// components/core/Card.jsx
try { (() => {
function Card({
  children,
  padding = 'md',
  variant = 'default',
  style
}) {
  const paddings = {
    sm: 'var(--space-4)',
    md: 'var(--space-6)',
    lg: 'var(--space-8)'
  };
  const variants = {
    default: {
      background: 'var(--surface-card)',
      boxShadow: 'var(--shadow-sm)',
      border: '1px solid var(--border-subtle)'
    },
    raised: {
      background: 'var(--surface-raised)',
      boxShadow: 'var(--shadow-md)',
      border: 'none'
    },
    sunken: {
      background: 'var(--surface-sunken)',
      boxShadow: 'none',
      border: '1px solid var(--border-subtle)'
    }
  };
  return React.createElement('div', {
    style: {
      borderRadius: 'var(--radius-lg)',
      padding: paddings[padding],
      ...variants[variant],
      ...style
    }
  }, children);
}
Object.assign(__ds_scope, { Card });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/core/Card.jsx", error: String((e && e.message) || e) }); }

// components/core/IconButton.jsx
try { (() => {
function IconButton({
  icon,
  size = 'md',
  variant = 'ghost',
  disabled,
  onClick,
  label
}) {
  const dims = {
    sm: 32,
    md: 40,
    lg: 48
  };
  const fs = {
    sm: 18,
    md: 20,
    lg: 24
  };
  const variants = {
    ghost: {
      background: 'transparent',
      color: 'var(--text-primary)'
    },
    filled: {
      background: 'var(--surface-raised)',
      color: 'var(--text-primary)',
      boxShadow: 'var(--shadow-sm)'
    },
    accent: {
      background: 'var(--accent-primary-subtle)',
      color: 'var(--accent-primary-hover)'
    }
  };
  const [isHover, setHover] = React.useState(false);
  const d = dims[size];
  return React.createElement('button', {
    type: 'button',
    disabled,
    onClick,
    'aria-label': label,
    onMouseEnter: () => setHover(true),
    onMouseLeave: () => setHover(false),
    style: {
      width: d,
      height: d,
      borderRadius: 'var(--radius-pill)',
      border: 'none',
      display: 'inline-flex',
      alignItems: 'center',
      justifyContent: 'center',
      cursor: disabled ? 'default' : 'pointer',
      opacity: disabled ? 0.4 : 1,
      transition: 'background var(--duration-fast) var(--ease-calm), transform var(--duration-fast) var(--ease-calm)',
      transform: isHover && !disabled ? 'scale(1.06)' : 'none',
      ...variants[variant],
      ...(isHover && !disabled && variant === 'ghost' ? {
        background: 'var(--surface-sunken)'
      } : {})
    }
  }, React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: fs[size]
    }
  }, icon));
}
Object.assign(__ds_scope, { IconButton });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/core/IconButton.jsx", error: String((e && e.message) || e) }); }

// components/core/Tag.jsx
try { (() => {
function Tag({
  children,
  selected,
  onClick
}) {
  const [isHover, setHover] = React.useState(false);
  return React.createElement('button', {
    type: 'button',
    onClick,
    onMouseEnter: () => setHover(true),
    onMouseLeave: () => setHover(false),
    style: {
      fontFamily: 'var(--font-body)',
      fontSize: 'var(--fs-body-sm)',
      fontWeight: 'var(--fw-medium)',
      padding: '7px 16px',
      borderRadius: 'var(--radius-pill)',
      cursor: 'pointer',
      border: selected ? '1px solid var(--accent-primary)' : '1px solid var(--border-default)',
      background: selected ? 'var(--accent-primary-subtle)' : isHover ? 'var(--surface-sunken)' : 'transparent',
      color: selected ? 'var(--pw-terracotta-700)' : 'var(--text-secondary)',
      transition: 'background var(--duration-fast) var(--ease-calm), border-color var(--duration-fast) var(--ease-calm)'
    }
  }, children);
}
Object.assign(__ds_scope, { Tag });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/core/Tag.jsx", error: String((e && e.message) || e) }); }

// components/feedback/HelperNote.jsx
try { (() => {
function HelperNote({
  children,
  icon
}) {
  return React.createElement('div', {
    style: {
      display: 'flex',
      alignItems: 'flex-start',
      gap: 6,
      fontFamily: 'var(--font-body)',
      fontSize: 'var(--fs-caption)',
      color: 'var(--text-muted)'
    }
  }, icon && React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 14,
      marginTop: 1
    }
  }, icon), React.createElement('span', null, children));
}
Object.assign(__ds_scope, { HelperNote });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/feedback/HelperNote.jsx", error: String((e && e.message) || e) }); }

// components/feedback/InlineErrorBanner.jsx
try { (() => {
function InlineErrorBanner({
  message,
  onDismiss
}) {
  return React.createElement('div', {
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 10,
      background: '#F6E4E2',
      border: '1px solid #E3B9B4',
      color: 'var(--status-danger)',
      borderRadius: 'var(--radius-md)',
      padding: '12px 14px',
      fontFamily: 'var(--font-body)',
      fontSize: 'var(--fs-body-sm)'
    }
  }, React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 18
    }
  }, 'error'), React.createElement('span', {
    style: {
      flex: 1
    }
  }, message), onDismiss && React.createElement('button', {
    type: 'button',
    onClick: onDismiss,
    'aria-label': 'Dismiss',
    style: {
      background: 'none',
      border: 'none',
      cursor: 'pointer',
      color: 'inherit',
      display: 'flex'
    }
  }, React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 18
    }
  }, 'close')));
}
Object.assign(__ds_scope, { InlineErrorBanner });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/feedback/InlineErrorBanner.jsx", error: String((e && e.message) || e) }); }

// components/feedback/ProgressIndicator.jsx
try { (() => {
function ProgressIndicator({
  value,
  indeterminate
}) {
  return React.createElement('div', {
    style: {
      width: '100%',
      height: 6,
      borderRadius: 'var(--radius-pill)',
      background: 'var(--surface-sunken)',
      overflow: 'hidden',
      position: 'relative'
    }
  }, React.createElement('div', {
    style: {
      position: 'absolute',
      top: 0,
      bottom: 0,
      left: 0,
      borderRadius: 'var(--radius-pill)',
      background: 'var(--accent-primary)',
      width: indeterminate ? '40%' : `${Math.max(0, Math.min(100, value))}%`,
      animation: indeterminate ? 'pw-indeterminate 1.2s var(--ease-calm) infinite' : 'none',
      transition: indeterminate ? 'none' : 'width var(--duration-base) var(--ease-calm)'
    }
  }), indeterminate && React.createElement('style', null, '@keyframes pw-indeterminate{0%{left:-40%}100%{left:100%}}'));
}
Object.assign(__ds_scope, { ProgressIndicator });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/feedback/ProgressIndicator.jsx", error: String((e && e.message) || e) }); }

// components/feedback/Toast.jsx
try { (() => {
function Toast({
  message,
  tone = 'default',
  icon,
  visible = true
}) {
  const tones = {
    default: {
      background: 'var(--surface-inverse)',
      color: 'var(--text-inverse)'
    },
    success: {
      background: 'var(--pw-sage-700)',
      color: 'var(--text-inverse)'
    },
    danger: {
      background: 'var(--status-danger)',
      color: 'var(--text-inverse)'
    }
  };
  return React.createElement('div', {
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 10,
      fontFamily: 'var(--font-body)',
      fontSize: 'var(--fs-body-sm)',
      padding: '12px 18px',
      borderRadius: 'var(--radius-md)',
      boxShadow: 'var(--shadow-lg)',
      ...tones[tone],
      opacity: visible ? 1 : 0,
      transform: visible ? 'translateY(0)' : 'translateY(8px)',
      transition: 'opacity var(--duration-base) var(--ease-calm), transform var(--duration-base) var(--ease-calm)'
    }
  }, icon && React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 18
    }
  }, icon), message);
}
Object.assign(__ds_scope, { Toast });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/feedback/Toast.jsx", error: String((e && e.message) || e) }); }

// components/feedback/Tooltip.jsx
try { (() => {
function Tooltip({
  children,
  label,
  side = 'top'
}) {
  const [show, setShow] = React.useState(false);
  const pos = {
    top: {
      bottom: '100%',
      left: '50%',
      transform: 'translateX(-50%)',
      marginBottom: 8
    },
    bottom: {
      top: '100%',
      left: '50%',
      transform: 'translateX(-50%)',
      marginTop: 8
    }
  };
  return React.createElement('span', {
    style: {
      position: 'relative',
      display: 'inline-flex'
    },
    onMouseEnter: () => setShow(true),
    onMouseLeave: () => setShow(false)
  }, children, React.createElement('span', {
    style: {
      position: 'absolute',
      ...pos[side],
      background: 'var(--surface-inverse)',
      color: 'var(--text-inverse)',
      fontFamily: 'var(--font-body)',
      fontSize: 'var(--fs-caption)',
      padding: '6px 10px',
      borderRadius: 'var(--radius-sm)',
      whiteSpace: 'nowrap',
      pointerEvents: 'none',
      boxShadow: 'var(--shadow-md)',
      opacity: show ? 1 : 0,
      transition: 'opacity var(--duration-fast) var(--ease-calm)'
    }
  }, label));
}
Object.assign(__ds_scope, { Tooltip });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/feedback/Tooltip.jsx", error: String((e && e.message) || e) }); }

// components/forms/Checkbox.jsx
try { (() => {
function Checkbox({
  label,
  checked,
  onChange,
  disabled
}) {
  return React.createElement('label', {
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 10,
      cursor: disabled ? 'default' : 'pointer',
      fontFamily: 'var(--font-body)',
      opacity: disabled ? 0.5 : 1
    }
  }, React.createElement('span', {
    onClick: () => !disabled && onChange && onChange(!checked),
    style: {
      width: 20,
      height: 20,
      borderRadius: 6,
      display: 'inline-flex',
      alignItems: 'center',
      justifyContent: 'center',
      border: `1.5px solid ${checked ? 'var(--accent-primary)' : 'var(--border-default)'}`,
      background: checked ? 'var(--accent-primary)' : 'transparent',
      transition: 'background var(--duration-fast) var(--ease-calm), border-color var(--duration-fast) var(--ease-calm)'
    }
  }, checked && React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 15,
      color: 'var(--text-on-accent)'
    }
  }, 'check')), label && React.createElement('span', {
    style: {
      fontSize: 'var(--fs-body)',
      color: 'var(--text-primary)'
    }
  }, label));
}
Object.assign(__ds_scope, { Checkbox });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/Checkbox.jsx", error: String((e && e.message) || e) }); }

// components/forms/Input.jsx
try { (() => {
function Input({
  label,
  placeholder,
  value,
  onChange,
  type = 'text',
  icon,
  error,
  disabled
}) {
  const [focused, setFocused] = React.useState(false);
  return React.createElement('label', {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 6,
      fontFamily: 'var(--font-body)'
    }
  }, label && React.createElement('span', {
    style: {
      fontSize: 'var(--fs-body-sm)',
      fontWeight: 'var(--fw-medium)',
      color: 'var(--text-secondary)'
    }
  }, label), React.createElement('div', {
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 8,
      background: 'var(--surface-sunken)',
      border: `1px solid ${error ? 'var(--status-danger)' : focused ? 'var(--accent-primary)' : 'var(--border-subtle)'}`,
      borderRadius: 'var(--radius-md)',
      padding: '10px 14px',
      transition: 'border-color var(--duration-fast) var(--ease-calm)',
      opacity: disabled ? 0.5 : 1
    }
  }, icon && React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 18,
      color: 'var(--text-muted)'
    }
  }, icon), React.createElement('input', {
    type,
    value,
    placeholder,
    disabled,
    onChange: e => onChange && onChange(e.target.value),
    onFocus: () => setFocused(true),
    onBlur: () => setFocused(false),
    style: {
      border: 'none',
      outline: 'none',
      background: 'transparent',
      font: 'inherit',
      fontSize: 'var(--fs-body)',
      color: 'var(--text-primary)',
      width: '100%'
    }
  })), error && React.createElement('span', {
    style: {
      fontSize: 'var(--fs-caption)',
      color: 'var(--status-danger)'
    }
  }, error));
}
Object.assign(__ds_scope, { Input });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/Input.jsx", error: String((e && e.message) || e) }); }

// components/forms/Radio.jsx
try { (() => {
function Radio({
  label,
  checked,
  onChange,
  name,
  disabled
}) {
  return React.createElement('label', {
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 10,
      cursor: disabled ? 'default' : 'pointer',
      fontFamily: 'var(--font-body)',
      opacity: disabled ? 0.5 : 1
    }
  }, React.createElement('span', {
    onClick: () => !disabled && onChange && onChange(),
    style: {
      width: 20,
      height: 20,
      borderRadius: '50%',
      display: 'inline-flex',
      alignItems: 'center',
      justifyContent: 'center',
      border: `1.5px solid ${checked ? 'var(--accent-primary)' : 'var(--border-default)'}`,
      transition: 'border-color var(--duration-fast) var(--ease-calm)'
    }
  }, checked && React.createElement('span', {
    style: {
      width: 10,
      height: 10,
      borderRadius: '50%',
      background: 'var(--accent-primary)'
    }
  })), label && React.createElement('span', {
    style: {
      fontSize: 'var(--fs-body)',
      color: 'var(--text-primary)'
    }
  }, label));
}
Object.assign(__ds_scope, { Radio });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/Radio.jsx", error: String((e && e.message) || e) }); }

// components/forms/Select.jsx
try { (() => {
function Select({
  label,
  value,
  onChange,
  options = []
}) {
  return React.createElement('label', {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 6,
      fontFamily: 'var(--font-body)'
    }
  }, label && React.createElement('span', {
    style: {
      fontSize: 'var(--fs-body-sm)',
      fontWeight: 'var(--fw-medium)',
      color: 'var(--text-secondary)'
    }
  }, label), React.createElement('div', {
    style: {
      position: 'relative'
    }
  }, React.createElement('select', {
    value,
    onChange: e => onChange && onChange(e.target.value),
    style: {
      appearance: 'none',
      width: '100%',
      background: 'var(--surface-sunken)',
      border: '1px solid var(--border-subtle)',
      borderRadius: 'var(--radius-md)',
      padding: '10px 36px 10px 14px',
      fontSize: 'var(--fs-body)',
      color: 'var(--text-primary)',
      font: 'inherit',
      cursor: 'pointer'
    }
  }, options.map(o => React.createElement('option', {
    key: o.value,
    value: o.value
  }, o.label))), React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      position: 'absolute',
      right: 10,
      top: '50%',
      transform: 'translateY(-50%)',
      fontSize: 18,
      color: 'var(--text-muted)',
      pointerEvents: 'none'
    }
  }, 'expand_more')));
}
Object.assign(__ds_scope, { Select });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/Select.jsx", error: String((e && e.message) || e) }); }

// components/forms/Switch.jsx
try { (() => {
function Switch({
  checked,
  onChange,
  label,
  disabled
}) {
  return React.createElement('label', {
    style: {
      display: 'inline-flex',
      alignItems: 'center',
      gap: 10,
      cursor: disabled ? 'default' : 'pointer',
      fontFamily: 'var(--font-body)',
      opacity: disabled ? 0.5 : 1
    }
  }, label && React.createElement('span', {
    style: {
      fontSize: 'var(--fs-body)',
      color: 'var(--text-primary)'
    }
  }, label), React.createElement('span', {
    onClick: () => !disabled && onChange && onChange(!checked),
    style: {
      width: 42,
      height: 24,
      borderRadius: 'var(--radius-pill)',
      position: 'relative',
      flexShrink: 0,
      background: checked ? 'var(--accent-primary)' : 'var(--border-default)',
      transition: 'background var(--duration-fast) var(--ease-calm)'
    }
  }, React.createElement('span', {
    style: {
      position: 'absolute',
      top: 3,
      left: checked ? 21 : 3,
      width: 18,
      height: 18,
      borderRadius: '50%',
      background: '#fff',
      boxShadow: 'var(--shadow-sm)',
      transition: 'left var(--duration-fast) var(--ease-calm)'
    }
  })));
}
Object.assign(__ds_scope, { Switch });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/Switch.jsx", error: String((e && e.message) || e) }); }

// components/media/PhotoPreview.jsx
try { (() => {
function PhotoPreview({
  state = 'empty',
  src,
  alt = '',
  onSelect,
  onRetry,
  size = 200
}) {
  const base = {
    width: size,
    height: size,
    borderRadius: 'var(--radius-lg)',
    overflow: 'hidden',
    position: 'relative',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontFamily: 'var(--font-body)'
  };
  if (state === 'loaded') {
    return React.createElement('div', {
      style: {
        ...base,
        background: 'var(--surface-sunken)'
      }
    }, src ? React.createElement('img', {
      src,
      alt,
      style: {
        width: '100%',
        height: '100%',
        objectFit: 'cover'
      }
    }) : React.createElement('div', {
      style: {
        width: '100%',
        height: '100%',
        background: 'linear-gradient(135deg,#caa27a,#8a6a52)'
      }
    }), onSelect && React.createElement('button', {
      type: 'button',
      onClick: onSelect,
      'aria-label': 'Change photo',
      style: {
        position: 'absolute',
        bottom: 10,
        right: 10,
        width: 36,
        height: 36,
        borderRadius: '50%',
        border: 'none',
        background: 'rgba(33,31,27,0.55)',
        color: '#fff',
        cursor: 'pointer',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        backdropFilter: 'blur(4px)'
      }
    }, React.createElement('span', {
      className: 'material-symbols-rounded',
      style: {
        fontSize: 18
      }
    }, 'edit')));
  }
  if (state === 'error') {
    return React.createElement('div', {
      style: {
        ...base,
        background: '#F6E4E2',
        border: '1px solid #E3B9B4',
        flexDirection: 'column',
        gap: 10,
        cursor: onRetry ? 'pointer' : 'default'
      },
      onClick: onRetry
    }, React.createElement('span', {
      className: 'material-symbols-rounded',
      style: {
        fontSize: 32,
        color: 'var(--status-danger)'
      }
    }, 'broken_image'), React.createElement('span', {
      style: {
        fontSize: 'var(--fs-body-sm)',
        color: 'var(--status-danger)',
        textAlign: 'center',
        padding: '0 16px'
      }
    }, "Couldn't load this photo"), onRetry && React.createElement('span', {
      style: {
        fontSize: 'var(--fs-caption)',
        color: 'var(--status-danger)',
        fontWeight: 'var(--fw-semibold)',
        textDecoration: 'underline'
      }
    }, 'Try again'));
  }
  return React.createElement('div', {
    style: {
      ...base,
      background: 'var(--surface-sunken)',
      border: '1.5px dashed var(--border-default)',
      flexDirection: 'column',
      gap: 10,
      cursor: onSelect ? 'pointer' : 'default'
    },
    onClick: onSelect
  }, React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 32,
      color: 'var(--text-muted)'
    }
  }, 'add_photo_alternate'), React.createElement('span', {
    style: {
      fontSize: 'var(--fs-body-sm)',
      color: 'var(--text-muted)',
      textAlign: 'center',
      padding: '0 16px'
    }
  }, 'No photo yet'));
}
Object.assign(__ds_scope, { PhotoPreview });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/media/PhotoPreview.jsx", error: String((e && e.message) || e) }); }

// components/navigation/Tabs.jsx
try { (() => {
function Tabs({
  tabs = [],
  value,
  onChange
}) {
  return React.createElement('div', {
    style: {
      display: 'flex',
      gap: 'var(--space-2)',
      borderBottom: '1px solid var(--border-subtle)'
    }
  }, tabs.map(t => {
    const active = t.value === value;
    return React.createElement('button', {
      key: t.value,
      onClick: () => onChange && onChange(t.value),
      style: {
        background: 'none',
        border: 'none',
        cursor: 'pointer',
        font: 'inherit',
        fontFamily: 'var(--font-body)',
        fontSize: 'var(--fs-body)',
        fontWeight: active ? 'var(--fw-semibold)' : 'var(--fw-regular)',
        color: active ? 'var(--text-primary)' : 'var(--text-muted)',
        padding: '10px 6px',
        position: 'relative',
        transition: 'color var(--duration-fast) var(--ease-calm)'
      }
    }, t.label, active && React.createElement('span', {
      style: {
        position: 'absolute',
        left: 0,
        right: 0,
        bottom: -1,
        height: 2,
        background: 'var(--accent-primary)',
        borderRadius: 2
      }
    }));
  }));
}
Object.assign(__ds_scope, { Tabs });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/navigation/Tabs.jsx", error: String((e && e.message) || e) }); }

// components/overlay/Dialog.jsx
try { (() => {
function Dialog({
  open,
  title,
  children,
  onClose,
  actions
}) {
  if (!open) return null;
  return React.createElement('div', {
    style: {
      position: 'fixed',
      inset: 0,
      background: 'rgba(33,31,27,0.35)',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      zIndex: 100,
      backdropFilter: 'blur(2px)'
    },
    onClick: onClose
  }, React.createElement('div', {
    onClick: e => e.stopPropagation(),
    style: {
      background: 'var(--surface-raised)',
      borderRadius: 'var(--radius-lg)',
      boxShadow: 'var(--shadow-lg)',
      padding: 'var(--space-6)',
      width: 360,
      maxWidth: '90vw',
      fontFamily: 'var(--font-body)'
    }
  }, title && React.createElement('div', {
    style: {
      fontFamily: 'var(--font-display)',
      fontSize: 'var(--fs-title)',
      color: 'var(--text-primary)',
      marginBottom: 'var(--space-3)'
    }
  }, title), React.createElement('div', {
    style: {
      fontSize: 'var(--fs-body)',
      color: 'var(--text-secondary)',
      marginBottom: 'var(--space-6)'
    }
  }, children), actions && React.createElement('div', {
    style: {
      display: 'flex',
      justifyContent: 'flex-end',
      gap: 'var(--space-3)'
    }
  }, actions)));
}
Object.assign(__ds_scope, { Dialog });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/overlay/Dialog.jsx", error: String((e && e.message) || e) }); }

// ui_kits/photo-widget-app/CustomizeScreen.jsx
try { (() => {
function CustomizeScreen({
  onBack
}) {
  const {
    IconButton,
    Tabs,
    Radio,
    Switch,
    Input,
    Button,
    Card
  } = window.PhotoWidgetDesignSystem_69a50d;
  const [tab, setTab] = React.useState('frame');
  const [shape, setShape] = React.useState('rounded');
  const [caption, setCaption] = React.useState('Sunday, at the lake');
  const [showCaption, setShowCaption] = React.useState(true);
  const [autoRefresh, setAutoRefresh] = React.useState(false);
  return React.createElement('div', {
    style: {
      width: '100%',
      height: '100%',
      background: 'var(--surface-app)',
      display: 'flex',
      flexDirection: 'column'
    }
  }, React.createElement(window.StatusBar, {
    dark: true
  }), React.createElement('div', {
    style: {
      display: 'flex',
      alignItems: 'center',
      gap: 8,
      padding: '4px 12px'
    }
  }, React.createElement(IconButton, {
    icon: 'arrow_back',
    label: 'Back',
    onClick: onBack
  }), React.createElement('div', {
    style: {
      fontFamily: 'var(--font-display)',
      fontSize: 20,
      color: 'var(--text-primary)'
    }
  }, 'Customize widget')), React.createElement('div', {
    style: {
      padding: '8px 20px'
    }
  }, React.createElement('div', {
    style: {
      width: 140,
      height: 140,
      margin: '0 auto 20px',
      borderRadius: shape === 'rounded' ? 'var(--radius-lg)' : shape === 'round' ? '50%' : '0px',
      overflow: 'hidden',
      boxShadow: 'var(--shadow-md)',
      background: 'linear-gradient(135deg,#caa27a,#8a6a52)'
    }
  }), React.createElement(Tabs, {
    tabs: [{
      label: 'Frame',
      value: 'frame'
    }, {
      label: 'Caption',
      value: 'caption'
    }, {
      label: 'Behavior',
      value: 'behavior'
    }],
    value: tab,
    onChange: setTab
  })), React.createElement('div', {
    style: {
      flex: 1,
      padding: '20px',
      display: 'flex',
      flexDirection: 'column',
      gap: 16,
      overflow: 'auto'
    }
  }, tab === 'frame' && React.createElement(Card, {
    padding: 'sm'
  }, React.createElement('div', {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 12
    }
  }, React.createElement(Radio, {
    name: 'shape',
    label: 'Rounded',
    checked: shape === 'rounded',
    onChange: () => setShape('rounded')
  }), React.createElement(Radio, {
    name: 'shape',
    label: 'Square',
    checked: shape === 'square',
    onChange: () => setShape('square')
  }), React.createElement(Radio, {
    name: 'shape',
    label: 'Round',
    checked: shape === 'round',
    onChange: () => setShape('round')
  }))), tab === 'caption' && React.createElement('div', {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 16
    }
  }, React.createElement(Switch, {
    label: 'Show caption',
    checked: showCaption,
    onChange: setShowCaption
  }), showCaption && React.createElement(Input, {
    label: 'Caption',
    value: caption,
    onChange: setCaption,
    icon: 'edit'
  })), tab === 'behavior' && React.createElement('div', {
    style: {
      display: 'flex',
      flexDirection: 'column',
      gap: 16
    }
  }, React.createElement(Switch, {
    label: 'Auto-refresh from album',
    checked: autoRefresh,
    onChange: setAutoRefresh
  }))), React.createElement('div', {
    style: {
      padding: '0 20px 28px'
    }
  }, React.createElement(Button, {
    variant: 'primary',
    size: 'lg',
    style: {
      width: '100%'
    }
  }, 'Done')));
}
window.CustomizeScreen = CustomizeScreen;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/photo-widget-app/CustomizeScreen.jsx", error: String((e && e.message) || e) }); }

// ui_kits/photo-widget-app/HomeScreen.jsx
try { (() => {
function HomeScreen({
  onCustomize
}) {
  const {
    IconButton
  } = window.PhotoWidgetDesignSystem_69a50d;
  return React.createElement('div', {
    style: {
      width: '100%',
      height: '100%',
      backgroundImage: 'linear-gradient(180deg,#3a4a5c 0%,#1c2530 100%)',
      display: 'flex',
      flexDirection: 'column',
      position: 'relative'
    }
  }, React.createElement(window.StatusBar, null), React.createElement('div', {
    style: {
      flex: 1,
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      padding: 28
    }
  }, React.createElement('div', {
    onClick: onCustomize,
    style: {
      width: 210,
      height: 210,
      borderRadius: 'var(--radius-lg)',
      overflow: 'hidden',
      boxShadow: '0 18px 40px rgba(0,0,0,0.4)',
      cursor: 'pointer',
      position: 'relative',
      background: 'linear-gradient(135deg,#caa27a,#8a6a52)'
    }
  }, React.createElement('div', {
    style: {
      position: 'absolute',
      inset: 0,
      display: 'flex',
      alignItems: 'flex-end',
      padding: 14
    }
  }, React.createElement('div', {
    style: {
      fontFamily: 'var(--font-display)',
      fontStyle: 'italic',
      color: '#fff',
      fontSize: 15,
      textShadow: '0 1px 6px rgba(0,0,0,0.4)'
    }
  }, 'Sunday, at the lake')))), React.createElement('div', {
    style: {
      display: 'flex',
      justifyContent: 'center',
      gap: 14,
      paddingBottom: 30
    }
  }, ['message', 'camera_alt', 'photo_camera', 'call'].map((ic, i) => React.createElement(IconButton, {
    key: i,
    icon: ic,
    variant: 'filled',
    label: ic
  }))));
}
window.HomeScreen = HomeScreen;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/photo-widget-app/HomeScreen.jsx", error: String((e && e.message) || e) }); }

// ui_kits/photo-widget-app/OnboardingScreen.jsx
try { (() => {
function OnboardingScreen({
  onNext
}) {
  const {
    Button
  } = window.PhotoWidgetDesignSystem_69a50d;
  return React.createElement('div', {
    style: {
      width: '100%',
      height: '100%',
      background: 'var(--pw-terracotta-500)',
      display: 'flex',
      flexDirection: 'column'
    }
  }, React.createElement(window.StatusBar, null), React.createElement('div', {
    style: {
      flex: 1,
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      padding: 32,
      textAlign: 'center',
      gap: 16
    }
  }, React.createElement('div', {
    style: {
      fontFamily: 'var(--font-display)',
      fontStyle: 'italic',
      fontWeight: 500,
      fontSize: 40,
      color: 'var(--pw-cream-50)'
    }
  }, 'Photo Widget'), React.createElement('div', {
    style: {
      fontFamily: 'var(--font-body)',
      fontSize: 16,
      color: 'var(--pw-cream-100)',
      lineHeight: 1.55,
      maxWidth: 260
    }
  }, 'Pick a photo. Resize it. Let it sit there.')), React.createElement('div', {
    style: {
      padding: '0 24px 40px'
    }
  }, React.createElement(Button, {
    variant: 'ghost',
    size: 'lg',
    onClick: onNext,
    style: {
      width: '100%',
      background: 'var(--pw-cream-50)',
      color: 'var(--pw-terracotta-700)'
    }
  }, 'Get started')));
}
window.OnboardingScreen = OnboardingScreen;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/photo-widget-app/OnboardingScreen.jsx", error: String((e && e.message) || e) }); }

// ui_kits/photo-widget-app/PhoneFrame.jsx
try { (() => {
function PhoneFrame({
  children
}) {
  return React.createElement('div', {
    style: {
      width: 360,
      height: 740,
      borderRadius: 44,
      background: '#0c0c0c',
      padding: 10,
      boxShadow: '0 24px 60px rgba(33,31,27,0.35)',
      position: 'relative',
      flexShrink: 0
    }
  }, React.createElement('div', {
    style: {
      width: '100%',
      height: '100%',
      borderRadius: 34,
      overflow: 'hidden',
      position: 'relative',
      background: '#000'
    }
  }, children));
}
window.PhoneFrame = PhoneFrame;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/photo-widget-app/PhoneFrame.jsx", error: String((e && e.message) || e) }); }

// ui_kits/photo-widget-app/StatusBar.jsx
try { (() => {
function StatusBar({
  dark
}) {
  const color = dark ? 'var(--text-primary)' : '#fff';
  return React.createElement('div', {
    style: {
      display: 'flex',
      justifyContent: 'space-between',
      padding: '14px 22px 4px',
      fontFamily: 'var(--font-body)',
      fontSize: 13,
      fontWeight: 600,
      color
    }
  }, React.createElement('span', null, '9:41'), React.createElement('span', {
    style: {
      display: 'flex',
      gap: 6
    }
  }, React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 16
    }
  }, 'signal_cellular_alt'), React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 16
    }
  }, 'wifi'), React.createElement('span', {
    className: 'material-symbols-rounded',
    style: {
      fontSize: 16
    }
  }, 'battery_full')));
}
window.StatusBar = StatusBar;
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/photo-widget-app/StatusBar.jsx", error: String((e && e.message) || e) }); }

__ds_ns.AlignmentPad = __ds_scope.AlignmentPad;

__ds_ns.CharacterCounter = __ds_scope.CharacterCounter;

__ds_ns.Chip = __ds_scope.Chip;

__ds_ns.FrameStyleSelector = __ds_scope.FrameStyleSelector;

__ds_ns.RotationControl = __ds_scope.RotationControl;

__ds_ns.SegmentedControl = __ds_scope.SegmentedControl;

__ds_ns.Slider = __ds_scope.Slider;

__ds_ns.SliderPresets = __ds_scope.SliderPresets;

__ds_ns.ValueReadout = __ds_scope.ValueReadout;

__ds_ns.Badge = __ds_scope.Badge;

__ds_ns.Button = __ds_scope.Button;

__ds_ns.Card = __ds_scope.Card;

__ds_ns.IconButton = __ds_scope.IconButton;

__ds_ns.Tag = __ds_scope.Tag;

__ds_ns.HelperNote = __ds_scope.HelperNote;

__ds_ns.InlineErrorBanner = __ds_scope.InlineErrorBanner;

__ds_ns.ProgressIndicator = __ds_scope.ProgressIndicator;

__ds_ns.Toast = __ds_scope.Toast;

__ds_ns.Tooltip = __ds_scope.Tooltip;

__ds_ns.Checkbox = __ds_scope.Checkbox;

__ds_ns.Input = __ds_scope.Input;

__ds_ns.Radio = __ds_scope.Radio;

__ds_ns.Select = __ds_scope.Select;

__ds_ns.Switch = __ds_scope.Switch;

__ds_ns.PhotoPreview = __ds_scope.PhotoPreview;

__ds_ns.Tabs = __ds_scope.Tabs;

__ds_ns.Dialog = __ds_scope.Dialog;

})();
