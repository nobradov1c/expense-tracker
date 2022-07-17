import { TextField } from "@mui/material";
import { FieldHookConfig, useField } from "formik";

type Props = FieldHookConfig<string> & {
  label: string;
  variant: "standard" | "filled" | "outlined" | undefined;
  className?: string;
};

function MyTextInput(props: Props) {
  const { label, placeholder, variant, className } = props;
  const [field, meta] = useField(props);

  return (
    <TextField
      className={className}
      label={label}
      placeholder={placeholder}
      required
      variant={variant}
      error={meta.touched && !!meta.error}
      helperText={meta.error}
      {...field}
    />
  );
}

export default MyTextInput;
