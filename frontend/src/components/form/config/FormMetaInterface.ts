export interface FormMetaInterface {
  isSubmitting: boolean;
  isSubmitted: boolean;
  submitStatus: undefined | "success" | "error";
  submitStatusMessage: string | null;
  errorResponse: any;
  //   who knows what kind of error response we'll get from the server
}

export const defaultFormMetaData = {
  isSubmitting: false,
  isSubmitted: false,
  submitStatus: undefined,
  submitStatusMessage: null,
  errorResponse: null,
} as FormMetaInterface;

export const submittedOnSuccessFormMetaData = {
  ...defaultFormMetaData,
  isSubmitted: true,
  submitStatus: "success",
  submitStatusMessage: "Form submitted successfully",
} as FormMetaInterface;

export const submittedOnErrorFormMetaData = {
  ...defaultFormMetaData,
  isSubmitted: true,
  submitStatus: "error",
  submitStatusMessage: "Form submission failed",
} as FormMetaInterface;

export const submittingFormMetaData = {
  ...defaultFormMetaData,
  isSubmitting: true,
} as FormMetaInterface;
