/**
 * @startingPoint section="Core" viewport="700x120"
 */
export interface ButtonProps {
  children?: React.ReactNode;
  variant?: 'primary' | 'secondary' | 'outline' | 'ghost';
  size?: 'sm' | 'md' | 'lg';
  /** Optional Material Symbols icon name shown before the label */
  icon?: string;
  disabled?: boolean;
  onClick?: () => void;
  type?: 'button' | 'submit';
}
