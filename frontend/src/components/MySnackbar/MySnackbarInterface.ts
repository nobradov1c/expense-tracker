export interface MySnackbarInterface {
  isOpen: boolean;
  severity: undefined | "success" | "error";
  message: string | null;
  errors?: string[] | null;
}

export const mySnackbarDefaultValues: MySnackbarInterface = {
  isOpen: false,
  severity: undefined,
  message: null,
  errors: null,
};
