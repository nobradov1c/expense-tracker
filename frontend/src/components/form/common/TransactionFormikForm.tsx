import { Button, DialogActions } from "@mui/material";
import { LoadingButton } from "@mui/lab";
import { Form, Formik, FormikHelpers } from "formik";
import { TransactionFormInterface } from "../../../models/forms/TransactionFormInterface";
import MySelect from "../inputs/MySelect";
import MyTextInput from "../inputs/MyTextInput";
import { GroupInterface } from "../../../data/models/GroupInterface";
import { transactionValidationSchema } from "../validation/transactionValidationSchema";

type Props = {
  isLoading: boolean;
  handleClose: () => void;
  onSubmit: (
    values: TransactionFormInterface,
    actions: FormikHelpers<{
      description: string;
      amount: number;
      groupId: number;
    }>
  ) => Promise<void>;
  groups: GroupInterface[];
  initialValues?: TransactionFormInterface;
};

const defaultInitialValues = {
  description: "",
  amount: 0,
  groupId: -1,
};

function TransactionFormikForm({
  isLoading,
  handleClose,
  onSubmit,
  groups,
  initialValues = defaultInitialValues,
}: Props) {
  return (
    <Formik
      initialValues={initialValues}
      validationSchema={transactionValidationSchema}
      onSubmit={onSubmit}
      validateOnMount={true}
    >
      {({ isValid, dirty }) => (
        <Form className="level is-mobile is-flex-direction-column">
          <MyTextInput
            className="field"
            label="Description"
            name="description"
            type="text"
            placeholder="Description"
            variant="outlined"
          />

          <MyTextInput
            className="field"
            label="Amount"
            name="amount"
            type="text"
            placeholder="Amount"
            variant="outlined"
          />

          <MySelect
            className="field"
            label="Group"
            labelId="expense-group-select"
            name="groupId"
            variant="outlined"
            options={groups}
          />

          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
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

export default TransactionFormikForm;
