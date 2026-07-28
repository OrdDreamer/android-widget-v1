export interface SliderPresetsProps {
  label?: string;
  value?: number;
  min?: number;
  max?: number;
  /** Quick-select preset values shown as a row of buttons under the slider, e.g. [0,15,30,45] */
  presets?: number[];
  unit?: string;
  onChange?: (value: number) => void;
}
