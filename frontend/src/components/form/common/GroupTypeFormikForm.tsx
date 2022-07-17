import { Button, DialogActions } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import { Form, Formik, FormikHelpers } from "formik";
import MyTextInput from "../inputs/MyTextInput";
import { GroupTypeFormInterface } from "../../../models/forms/GroupTypeFormInterface";
import { groupTypeValidationSchema } from "../validation/groupTypeValidationSchema";

type Props = {
  isLoading: boolean;
  handleClose: () => void;
  onSubmit: (
    values: GroupTypeFormInterface,
    actions: FormikHelpers<{
      name: string;
      description: string;
    }>
  ) => Promise<void>;
};

const initialValues = {
  name: "",
  description: "",
};

function GroupTypeFormikForm({ isLoading, handleClose, onSubmit }: Props) {
  return (
    <Formik
      initialValues={initialValues}
      validationSchema={groupTypeValidationSchema}
      onSubmit={onSubmit}
    >
      {({ isValid, dirty }) => (
        <Form className="level is-mobile is-flex-direction-column">
          <MyTextInput
            className="field"
            label="Name"
            name="name"
            type="text"
            placeholder="Name"
            variant="outlined"
          />

          <MyTextInput
            className="field"
            label="Description"
            name="description"
            type="text"
            placeholder="Description"
            variant="outlined"
          />

          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <LoadingButton
              loading={isLoading}
              disabled={!isValid || !dirty}
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

export default GroupTypeFormikForm;
