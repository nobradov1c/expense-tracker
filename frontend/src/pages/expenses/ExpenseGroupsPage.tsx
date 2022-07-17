import { AxiosError } from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Button } from "@mui/material";

import DataTable from "../../components/DataTable/DataTable";
import {
  createNewExpenseGroup,
  deleteAnExpenseGroup,
  getAllExpenseGroups,
  getExpenseGroupById,
  updateExpenseGroupById,
} from "../../services/expensesService";
import reactQueryConfig from "../../config/reactQueryConfig";
import {
  defaultFormMetaData,
  FormMetaInterface,
  submittedOnErrorFormMetaData,
  submittedOnSuccessFormMetaData,
  submittingFormMetaData,
} from "../../components/form/config/FormMetaInterface";
import { GroupTypeFormInterface } from "../../models/forms/GroupTypeFormInterface";
import MySnackbar from "../../components/MySnackbar/MySnackbar";
import CreateGroupFormDialog from "../../components/form/CreateGroupFormDialog";
import groupsColumns from "../../components/DataTable/config/groupsColumns";
import { GroupInterface } from "../../data/models/GroupInterface";
import ConfirmationDialog from "../../components/form/ConfirmationDialog";

function ExpenseGroupsPage() {
  const [
    createExpenseGroupFormDialogOpen,
    setCreateExpenseGroupFormDialogOpen,
  ] = useState<boolean>(false);
  const [
    updateExpenseGroupFormDialogOpen,
    setUpdateExpenseGroupFormDialogOpen,
  ] = useState<boolean>(false);
  const [
    createExpenseGroupFormDialogMeta,
    setCreateExpenseGroupFormDialogMeta,
  ] = useState<FormMetaInterface>(defaultFormMetaData);
  const [helperErrors, setHelperErrors] = useState<string[] | null>(null);
  const [groupId, setGroupId] = useState<number>(-1);
  const [groupObject, setGroupObject] = useState<GroupInterface>(
    {} as GroupInterface
  );
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const [confirmationDialogOpen, setConfirmationDialogOpen] =
    useState<boolean>(false);
  const [confirmationDialogTitle, setConfirmationDialogTitle] =
    useState<string>("");
  const [selectedId, setSelectedId] = useState<number>(-1);
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

  const closeUpdateExpenseGroupFormDialog = () => {
    setUpdateExpenseGroupFormDialogOpen(false);
  };

  const handleDetailsAction = (id: number) => {
    navigate(`/expense-groups/${id}`);
  };

  const handleEditAction = (id: number) => {
    setGroupId(id);

    getExpenseGroupById(id)
      .then((groupObject) => {
        setGroupObject(groupObject);
        setUpdateExpenseGroupFormDialogOpen(true);
      })
      .catch((error) => {
        setCreateExpenseGroupFormDialogMeta({
          ...submittedOnErrorFormMetaData,
          submitStatusMessage: "Error fetching expense group",
          errorResponse: error || "Error fetching expense group",
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
      });
  };

  const handleDeleteAnExpenseGroupMutation = useMutation(deleteAnExpenseGroup, {
    onSuccess: () => {
      queryClient.invalidateQueries("expenseGroups");
      queryClient.invalidateQueries("expenses");
    },
  });

  const handleDeleteAction = (id: number) => {
    setSelectedId(id);
    setConfirmationDialogTitle("Delete Expense Group with id = " + id);
    setConfirmationDialogOpen(true);
  };

  const handleDeleteActionConfirmationNo = () => {
    setConfirmationDialogOpen(false);
  };

  const handleDeleteActionConfirmationYes = () => {
    handleDeleteAnExpenseGroupMutation.mutate(selectedId);
    setConfirmationDialogOpen(false);
  };

  const columns = groupsColumns({
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
          ...submittedOnSuccessFormMetaData,
          submitStatusMessage: "Expense group created successfully",
        });

        // close form dialog
        closeCreateExpenseGroupFormDialog();
      },
      onError: (error: AxiosError) => {
        setCreateExpenseGroupFormDialogMeta({
          ...submittedOnErrorFormMetaData,
          submitStatusMessage: "Error creating expense group",
          errorResponse: error,
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

  const handleUpdateExpenseGroupByIdMutation = useMutation(
    updateExpenseGroupById,
    {
      onSuccess: () => {
        queryClient.invalidateQueries("expenseGroups");

        setCreateExpenseGroupFormDialogMeta({
          ...submittedOnSuccessFormMetaData,
          submitStatusMessage: "Expense group updated successfully",
        });

        closeUpdateExpenseGroupFormDialog();
      },

      onError: (error: AxiosError) => {
        setCreateExpenseGroupFormDialogMeta({
          ...submittedOnErrorFormMetaData,
          submitStatusMessage: "Error updating expense group",
          errorResponse: error,
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
    setCreateExpenseGroupFormDialogMeta(submittingFormMetaData);

    handleCreateNewExpenseGroupMutation.mutate(values);
  };

  const onUpdateExpenseGroupFormDialogSubmit = (
    values: GroupTypeFormInterface
  ) => {
    // reset form meta
    setCreateExpenseGroupFormDialogMeta(submittingFormMetaData);

    handleUpdateExpenseGroupByIdMutation.mutate({ id: groupId, values });
  };

  const handleSnackbarClose = () => {
    setCreateExpenseGroupFormDialogMeta(defaultFormMetaData);

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

      {/* edit form dialog */}
      <CreateGroupFormDialog
        title={`Edit Expense ${groupObject.name}`}
        isOpen={updateExpenseGroupFormDialogOpen}
        close={closeUpdateExpenseGroupFormDialog}
        formMeta={createExpenseGroupFormDialogMeta}
        onSubmit={onUpdateExpenseGroupFormDialogSubmit}
        initialValues={groupObject}
      />

      <ConfirmationDialog
        title={confirmationDialogTitle}
        isOpen={confirmationDialogOpen}
        handleYes={handleDeleteActionConfirmationYes}
        handleNo={handleDeleteActionConfirmationNo}
      />

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
