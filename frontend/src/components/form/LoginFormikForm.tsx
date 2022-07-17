import { DialogActions } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import { Form, Formik } from "formik";
import MyTextInput from "./inputs/MyTextInput";
import { loginRequestValidationSchema } from "./validation/loginRequestValidationSchema";
import { FormPropsInterface } from "./config/FormPropsInterface";

const defaultInitialValues = {
  email: "",
  password: "",
};

function LoginFormikForm({ isLoading, onSubmit }: FormPropsInterface) {
  return (
    <Formik
      initialValues={defaultInitialValues}
      validationSchema={loginRequestValidationSchema}
      onSubmit={onSubmit}
      validateOnMount={true}
    >
      {({ isValid, dirty }) => (
        <Form className="level is-mobile is-flex-direction-column">
          <MyTextInput
            className="field"
            label="Email"
            name="email"
            type="text"
            placeholder="Email"
            variant="outlined"
          />

          <MyTextInput
            className="field"
            label="Password"
            name="password"
            type="text"
            placeholder="Password"
            variant="outlined"
          />

          <DialogActions>
            <LoadingButton
              loading={isLoading}
              disabled={!isValid}
              type="submit"
            >
              Submit
            </LoadingButton>
          </DialogActions>
        </Form>
      )}
    </Formik>
  );
}

export default LoginFormikForm;
