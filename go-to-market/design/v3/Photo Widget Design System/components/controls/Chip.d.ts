export interface ChipProps {
  children?: React.ReactNode;
  /** Material Symbols icon name */
  icon?: string;
  selected?: boolean;
  onClick?: () => void;
}
