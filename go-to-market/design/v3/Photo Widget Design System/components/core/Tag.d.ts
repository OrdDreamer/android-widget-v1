export interface TagProps {
  children?: React.ReactNode;
  /** Selected/active state, e.g. current filter chip */
  selected?: boolean;
  onClick?: () => void;
}
