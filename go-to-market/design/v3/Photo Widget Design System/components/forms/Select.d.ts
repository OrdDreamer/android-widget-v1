export interface SelectOption { label: string; value: string; }
export interface SelectProps {
  label?: string;
  value?: string;
  onChange?: (value: string) => void;
  options?: SelectOption[];
}
