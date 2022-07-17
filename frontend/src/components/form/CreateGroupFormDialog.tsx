import { Dialog, DialogContent, DialogTitle } from "@mui/material";
// import { FormikHelpers } from "formik";
import { GroupTypeFormInterface } from "../../models/forms/GroupTypeFormInterface";
import GroupTypeFormikForm from "./common/GroupTypeFormikForm";
import { FormMetaInterface } from "./config/FormMetaInterface";

type Props = {
  title: string;
  isOpen: boolean;
  close: () => void;
  formMeta: FormMetaInterface;
  onSubmit: (values: GroupTypeFormInterface) => void;
  initialValues?: GroupTypeFormInterface;
};
const defaultInitialValues: GroupTypeFormInterface = {
  name: "",
  description: "",
};

function CreateGroupFormDialog({
  title,
  isOpen = false,
  close,
  formMeta,
  onSubmit: handleSubmit,
  initialValues = defaultInitialValues,
}: Props) {
  const onSubmit = async (
    values: GroupTypeFormInterface
    // actions: FormikHelpers<GroupTypeFormInterface>
  ) => {
    handleSubmit(values);
    // actions.resetForm();
  };

  return (
    <Dialog open={isOpen} onClose={close}>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent className="pt-4">
        <GroupTypeFormikForm
          isLoading={formMeta.isSubmitting}
          handleClose={close}
          onSubmit={onSubmit}
          initialValues={initialValues}
        />
      </DialogContent>
    </Dialog>
  );
}

export default CreateGroupFormDialog;
