export interface FormMetaInterface {
  isSubmitting: boolean;
  isSubmitted: boolean;
  submitStatus: undefined | "success" | "error";
  submitStatusMessage: string | null;
  errorResponse: any;
  //   who knows what kind of error response we'll get from the server
}
