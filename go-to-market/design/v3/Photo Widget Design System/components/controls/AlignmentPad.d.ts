export interface AlignmentPadProps {
  /** One of the 9 anchor positions, e.g. 'top-left' | 'top' | ... | 'center' | ... | 'bottom-right' */
  value?: string;
  onChange?: (value: string) => void;
}
