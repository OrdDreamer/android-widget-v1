export interface SegmentedOption { label: string; value: string; }
export interface SegmentedControlProps {
  /** 2–3 options */
  options: SegmentedOption[];
  value?: string;
  onChange?: (value: string) => void;
  /** Stretch to fill container width, each segment equal width */
  fullWidth?: boolean;
}
