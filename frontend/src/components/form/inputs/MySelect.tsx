import { FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { FieldHookConfig, useField } from "formik";
import { GroupInterface } from "../../../data/models/GroupInterface";

type Props = FieldHookConfig<string> & {
  label: string;
  variant: "standard" | "filled" | "outlined" | undefined;
  labelId: string;
  className?: string;
  options: GroupInterface[];
};

function MySelect(props: Props) {
  const { label, labelId, placeholder, variant, className, options } = props;
  const [field, meta] = useField(props);

  return (
    <FormControl fullWidth>
      <InputLabel id={labelId}>{label}</InputLabel>
      <Select
        className={className}
        label={label}
        labelId={labelId}
        placeholder={placeholder}
        required
        variant={variant}
        error={meta.touched && !!meta.error}
        {...field}
      >
        <MenuItem key={`option-${labelId}-none`} value={-1} selected>
          None
        </MenuItem>
        {options.map((option) => (
          <MenuItem key={`option-${labelId}-${option.id}`} value={option.id}>
            {option.name}
          </MenuItem>
        ))}
      </Select>
    </FormControl>
  );
}

export default MySelect;
