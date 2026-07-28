export interface IconButtonProps {
  /** Material Symbols icon name, e.g. "settings" */
  icon: string;
  size?: 'sm' | 'md' | 'lg';
  variant?: 'ghost' | 'filled' | 'accent';
  disabled?: boolean;
  onClick?: () => void;
  /** Accessible label (required — icon-only button) */
  label: string;
}
