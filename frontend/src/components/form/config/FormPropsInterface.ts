import { FormikHelpers } from "formik";
import { LoginRequestInterface } from "../../../data/models/request/LoginRequestInterface";

export interface FormPropsInterface {
  isLoading: boolean;
  onSubmit: (
    values: LoginRequestInterface,
    actions: FormikHelpers<LoginRequestInterface>
  ) => Promise<void>;
}
