import { AxiosError } from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Button } from "@mui/material";

import DataTable from "../../components/DataTable/DataTable";
import expenseGroupsColumns from "../../components/DataTable/config/expenseGroupsColumns";
import {
  createNewExpenseGroup,
  deleteAnExpenseGroup,
  getAllExpenseGroups,
} from "../../services/expensesService";
import reactQueryConfig from "../../config/reactQueryConfig";
import { FormMetaInterface } from "../../components/form/config/FormMetaInterface";
import { GroupTypeFormInterface } from "../../models/forms/GroupTypeFormInterface";
import MySnackbar from "../../components/MySnackbar/MySnackbar";
import CreateGroupFormDialog from "../../components/form/CreateGroupFormDialog";

function ExpenseGroupsPage() {
  const [
    createExpenseGroupFormDialogOpen,
    setCreateExpenseGroupFormDialogOpen,
  ] = useState<boolean>(false);
  const [
    createExpenseGroupFormDialogMeta,
    setCreateExpenseGroupFormDialogMeta,
  ] = useState<FormMetaInterface>({
    isSubmitting: false,
    isSubmitted: false,
    submitStatus: undefined,
    submitStatusMessage: null,
    errorResponse: null,
  });
  const [helperErrors, setHelperErrors] = useState<string[] | null>(null);
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const {
    data: expenseGroups,
    isFetching,
    isLoading,
  } = useQuery("expenseGroups", getAllExpenseGroups, reactQueryConfig);

  const openCreateExpenseGroupFormDialog = () => {
    setCreateExpenseGroupFormDialogOpen(true);
  };

  const closeCreateExpenseGroupFormDialog = () => {
    setCreateExpenseGroupFormDialogOpen(false);
  };

  const handleDetailsAction = (id: number) => {
    navigate(`/expense-groups/${id}`);
  };

  const handleEditAction = (id: number) => {
    // TODO: implement edit action
  };

  const handleDeleteAnExpenseGroupMutation = useMutation(deleteAnExpenseGroup, {
    onSuccess: () => {
      queryClient.invalidateQueries("expenseGroups");
      queryClient.invalidateQueries("expenses");
    },
  });

  const handleDeleteAction = (id: number) => {
    handleDeleteAnExpenseGroupMutation.mutate(id);
  };

  const columns = expenseGroupsColumns({
    handleEditAction,
    handleDetailsAction,
    handleDeleteAction,
  });

  const handleCreateNewExpenseGroupMutation = useMutation(
    createNewExpenseGroup,
    {
      onSuccess: () => {
        queryClient.invalidateQueries("expenseGroups");

        setCreateExpenseGroupFormDialogMeta({
          submitStatus: "success",
          submitStatusMessage: "Expense group created successfully",
          isSubmitting: false,
          isSubmitted: true,
          errorResponse: null,
        });

        // close form dialog
        closeCreateExpenseGroupFormDialog();
      },
      onError: (error: AxiosError) => {
        setCreateExpenseGroupFormDialogMeta({
          submitStatus: "error",
          submitStatusMessage: "Error creating expense group",
          errorResponse: error,
          isSubmitting: false,
          isSubmitted: true,
        });

        if (
          createExpenseGroupFormDialogMeta.errorResponse.response &&
          createExpenseGroupFormDialogMeta.errorResponse.response.data &&
          createExpenseGroupFormDialogMeta.errorResponse.response.data.errors
        ) {
          setHelperErrors(
            createExpenseGroupFormDialogMeta.errorResponse.response.data.errors
          );
        } else {
          setHelperErrors(null);
        }
      },
    }
  );

  const onCreateExpenseGroupFormDialogSubmit = (
    values: GroupTypeFormInterface
  ) => {
    // reset form meta
    setCreateExpenseGroupFormDialogMeta({
      isSubmitted: false,
      isSubmitting: true,
      submitStatus: undefined,
      submitStatusMessage: null,
      errorResponse: null,
    });

    handleCreateNewExpenseGroupMutation.mutate(values);
  };

  const handleSnackbarClose = () => {
    setCreateExpenseGroupFormDialogMeta({
      ...createExpenseGroupFormDialogMeta,
      isSubmitted: false,
    });

    setHelperErrors(null);
  };

  return (
    <>
      <div className="level">
        <div className="level-left">
          <div className="level-item">
            <h1>Expense Groups Page</h1>
          </div>
        </div>
        <div className="level-right">
          <div className="level-item">
            <Button
              variant="outlined"
              onClick={openCreateExpenseGroupFormDialog}
            >
              Add New
            </Button>
            <CreateGroupFormDialog
              title="New Expense Group"
              isOpen={createExpenseGroupFormDialogOpen}
              close={closeCreateExpenseGroupFormDialog}
              formMeta={createExpenseGroupFormDialogMeta}
              onSubmit={onCreateExpenseGroupFormDialogSubmit}
            />
          </div>
        </div>
      </div>

      <div className="data-table-container">
        <DataTable
          columns={columns}
          data={expenseGroups || []}
          isLoading={isFetching}
          linearProgress={!isLoading}
        />
      </div>

      <MySnackbar
        isOpen={createExpenseGroupFormDialogMeta.isSubmitted}
        handleSnackbarClose={handleSnackbarClose}
        severity={createExpenseGroupFormDialogMeta.submitStatus}
        message={createExpenseGroupFormDialogMeta.submitStatusMessage}
        errors={helperErrors}
      />
    </>
  );
}

export default ExpenseGroupsPage;
