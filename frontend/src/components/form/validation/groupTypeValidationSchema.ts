import * as yup from "yup";

export const groupTypeValidationSchema = yup.object().shape({
  description: yup.string().trim().required("Required"),
  name: yup.string().trim().required("Required"),
});
