import { AxiosError } from "axios";
import { FormikHelpers } from "formik";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import LoginFormikForm from "../components/form/LoginFormikForm";
import MySnackbar from "../components/MySnackbar/MySnackbar";
import {
  mySnackbarDefaultValues,
  MySnackbarInterface,
} from "../components/MySnackbar/MySnackbarInterface";
import { LoginResponseInterface } from "../data/models/LoginResponseInterface";
import { LoginRequestInterface } from "../data/models/request/LoginRequestInterface";
import { login } from "../services/security";

function LoginPage() {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [snackbarProps, setSnackbarProps] = useState<MySnackbarInterface>({
    ...mySnackbarDefaultValues,
  });
  const navigate = useNavigate();

  const handleSnackbarClose = () => {
    setSnackbarProps({
      ...snackbarProps,
      isOpen: false,
    });
  };

  const onSubmit = async (
    values: LoginRequestInterface,
    actions: FormikHelpers<LoginRequestInterface>
  ) => {
    setIsLoading(true);

    login(values)
      .then((res: LoginResponseInterface) => {
        setSnackbarProps({
          ...snackbarProps,
          isOpen: true,
          severity: "success",
          message: "Login successful",
        });
        actions.resetForm();

        // redirect to home page
        navigate(`/`);
      })
      .catch((err: AxiosError<{ errors: string[] }>) => {
        let errors = null;
        if (err.response && err.response.data && err.response.data.errors) {
          errors = err.response.data.errors;
        }
        setSnackbarProps({
          ...snackbarProps,
          isOpen: true,
          severity: "error",
          message: "Login failed",
          errors,
        });
      })
      .finally(() => {
        setIsLoading(false);
      });
  };

  return (
    <>
      <h1>Login page</h1>

      <LoginFormikForm isLoading={isLoading} onSubmit={onSubmit} />

      <MySnackbar
        {...snackbarProps}
        handleSnackbarClose={handleSnackbarClose}
      />
    </>
  );
}

export default LoginPage;
