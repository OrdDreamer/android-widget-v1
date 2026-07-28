/**
 * @startingPoint section="Forms" subtitle="Text field with label, icon and error state" viewport="700x140"
 */
export interface InputProps {
  label?: string;
  placeholder?: string;
  value?: string;
  onChange?: (value: string) => void;
  type?: 'text' | 'email' | 'password' | 'number';
  /** Material Symbols icon name shown at the start */
  icon?: string;
  error?: string;
  disabled?: boolean;
}
