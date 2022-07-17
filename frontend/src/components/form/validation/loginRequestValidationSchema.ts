import * as yup from "yup";

export const loginRequestValidationSchema = yup.object().shape({
  email: yup.string().trim().email().required("Required"),
  password: yup.string().trim().min(8).max(32).required("Required"),
});
