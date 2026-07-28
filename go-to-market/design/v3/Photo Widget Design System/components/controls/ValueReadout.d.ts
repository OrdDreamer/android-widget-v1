export interface ValueReadoutProps {
  label?: string;
  /** Pre-formatted display value, e.g. "45°" or "1.2×" */
  value: string | number;
  tone?: 'neutral' | 'accent';
}
