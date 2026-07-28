export interface RotationControlProps {
  /** Degrees, snapped to 45° steps around the full 360° circle (0, 45, 90…315) */
  value?: number;
  onChange?: (degrees: number) => void;
}
