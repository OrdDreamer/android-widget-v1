/**
 * @startingPoint section="Media" subtitle="Photo preview zone — empty, error, and loaded states" viewport="700x260"
 */
export interface PhotoPreviewProps {
  state?: 'empty' | 'error' | 'loaded';
  /** Image URL for the loaded state; omit to show a placeholder gradient */
  src?: string;
  alt?: string;
  /** Called when the empty or loaded state is tapped to choose/change a photo */
  onSelect?: () => void;
  /** Called when the error state's "Try again" is tapped */
  onRetry?: () => void;
  /** Square side length in px */
  size?: number;
}
