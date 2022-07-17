import * as yup from "yup";

const floatingPointRegex = /^\d+(\.\d{1,2})?$/;
// max 2 decimal places

export const transactionValidationSchema = yup.object().shape({
  description: yup.string().trim().required("Required"),
  amount: yup
    .string()
    .trim()
    .matches(floatingPointRegex, {
      message: "Number with max 2 decimal places",
    })
    .required("Required"),
});
