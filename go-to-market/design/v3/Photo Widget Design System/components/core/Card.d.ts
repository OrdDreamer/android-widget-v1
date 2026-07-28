/**
 * @startingPoint section="Core" subtitle="Soft surface container for photo previews and settings groups" viewport="700x220"
 */
export interface CardProps {
  children?: React.ReactNode;
  padding?: 'sm' | 'md' | 'lg';
  variant?: 'default' | 'raised' | 'sunken';
  style?: React.CSSProperties;
}
