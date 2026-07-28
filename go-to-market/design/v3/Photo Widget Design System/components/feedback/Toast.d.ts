export interface ToastProps {
  message: string;
  tone?: 'default' | 'success' | 'danger';
  /** Material Symbols icon name */
  icon?: string;
  visible?: boolean;
}
