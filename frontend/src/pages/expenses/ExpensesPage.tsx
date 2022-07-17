import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Button } from "@mui/material";

import DataTable from "../../components/DataTable/DataTable";
import {
  createNewExpense,
  deleteAnExpense,
  getAllExpenseGroups,
  getAllExpenses,
  getExpenseById,
  updateExpenseById,
} from "../../services/expensesService";
import reactQueryConfig from "../../config/reactQueryConfig";
import {
  defaultFormMetaData,
  FormMetaInterface,
  submittedOnErrorFormMetaData,
  submittedOnSuccessFormMetaData,
  submittingFormMetaData,
} from "../../components/form/config/FormMetaInterface";
import { AxiosError } from "axios";
import { TransactionFormInterface } from "../../models/forms/TransactionFormInterface";
import MySnackbar from "../../components/MySnackbar/MySnackbar";
import CreateTransactionFormDialog from "../../components/form/CreateTransactionFormDialog";
import transactionColumns from "../../components/DataTable/config/transactionColumns";
import { ExpenseInterface } from "../../data/models/ExpenseInterface";
import ConfirmationDialog from "../../components/form/ConfirmationDialog";

function ExpensesPage() {
  const [createExpenseFormDialogOpen, setCreateExpenseFormDialogOpen] =
    useState<boolean>(false);
  const [updateExpenseFormDialogOpen, setUpdateExpenseFormDialogOpen] =
    useState<boolean>(false);
  const [createExpenseFormDialogMeta, setCreateExpenseFormDialogMeta] =
    useState<FormMetaInterface>(defaultFormMetaData);
  const [updateFormTitle, setUpdateFormTitle] = useState<string>(
    "Edit Expense with id: -1"
  );
  const [transactionId, setTransactionId] = useState<number>(-1);
  const [transactionObject, setTransactionObject] = useState<ExpenseInterface>(
    {} as ExpenseInterface
  );

  const [helperErrors, setHelperErrors] = useState<string[] | null>(null);
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: expenseGroups } = useQuery(
    "expenseGroups",
    getAllExpenseGroups,
    reactQueryConfig
  );
  const [confirmationDialogOpen, setConfirmationDialogOpen] =
    useState<boolean>(false);
  const [confirmationDialogTitle, setConfirmationDialogTitle] =
    useState<string>("");
  const [selectedId, setSelectedId] = useState<number>(-1);
  const {
    data: expenses,
    isFetching,
    isLoading,
  } = useQuery("expenses", getAllExpenses, reactQueryConfig);

  const openCreateExpenseFormDialog = () => {
    setCreateExpenseFormDialogOpen(true);
  };

  const closeCreateExpenseFormDialog = () => {
    setCreateExpenseFormDialogOpen(false);
  };

  const closeUpdateExpenseFormDialog = () => {
    setUpdateExpenseFormDialogOpen(false);
  };

  const handleDetailsAction = (id: number) => {
    navigate(`/expenses/${id}`);
  };

  const handleEditAction = (id: number) => {
    setTransactionId(id);

    getExpenseById(id)
      .then((transaction) => {
        setTransactionObject(transaction);
        setUpdateFormTitle(`Edit Expense with id: ${id}`);
        setUpdateExpenseFormDialogOpen(true);
      })
      .catch((error) => {
        setCreateExpenseFormDialogMeta({
          ...submittedOnErrorFormMetaData,
          submitStatusMessage: "Error fetching expense group",
          errorResponse: error || "Error fetching expense group",
        });

        if (
          createExpenseFormDialogMeta.errorResponse.response &&
          createExpenseFormDialogMeta.errorResponse.response.data &&
          createExpenseFormDialogMeta.errorResponse.response.data.errors
        ) {
          setHelperErrors(
            createExpenseFormDialogMeta.errorResponse.response.data.errors
          );
        } else {
          setHelperErrors(null);
        }
      });
  };

  const handleUpdateExpenseByIdMutation = useMutation(updateExpenseById, {
    onSuccess: () => {
      queryClient.invalidateQueries("expenses");

      setCreateExpenseFormDialogMeta({
        ...submittedOnSuccessFormMetaData,
        submitStatusMessage: "Expense updated successfully",
      });

      closeUpdateExpenseFormDialog();
    },
    onError: (error: AxiosError) => {
      setCreateExpenseFormDialogMeta({
        ...submittedOnErrorFormMetaData,
        submitStatusMessage: "Error fetching expense group",
        errorResponse: error || "Error fetching expense group",
      });

      if (
        createExpenseFormDialogMeta.errorResponse.response &&
        createExpenseFormDialogMeta.errorResponse.response.data &&
        createExpenseFormDialogMeta.errorResponse.response.data.errors
      ) {
        setHelperErrors(
          createExpenseFormDialogMeta.errorResponse.response.data.errors
        );
      } else {
        setHelperErrors(null);
      }
    },
  });

  const onUpdateExpenseFormDialogSubmit = (
    values: TransactionFormInterface
  ) => {
    setCreateExpenseFormDialogMeta(submittingFormMetaData);

    handleUpdateExpenseByIdMutation.mutate({
      id: transactionId,
      values,
    });
  };

  const handleDeleteAnExpenseMutation = useMutation(deleteAnExpense, {
    onSuccess: () => {
      queryClient.invalidateQueries("expenses");
    },
  });

  const handleDeleteAction = (id: number) => {
    setSelectedId(id);
    setConfirmationDialogTitle("Delete Expense with id = " + id);
    setConfirmationDialogOpen(true);
  };

  const handleDeleteActionConfirmationNo = () => {
    setConfirmationDialogOpen(false);
  };

  const handleDeleteActionConfirmationYes = () => {
    handleDeleteAnExpenseMutation.mutate(selectedId);
    setConfirmationDialogOpen(false);
  };

  const columns = transactionColumns({
    handleEditAction,
    handleDetailsAction,
    handleDeleteAction,
  });

  const handleCreateNewExpenseMutation = useMutation(createNewExpense, {
    onSuccess: () => {
      queryClient.invalidateQueries("expenses");

      setCreateExpenseFormDialogMeta({
        ...submittedOnSuccessFormMetaData,
        submitStatusMessage: "Expense created successfully",
      });

      // close form dialog
      setCreateExpenseFormDialogOpen(false);
    },

    onError: (error: AxiosError) => {
      setCreateExpenseFormDialogMeta({
        ...submittedOnErrorFormMetaData,
        submitStatusMessage: "Error creating expense",
        errorResponse: error,
      });

      if (
        createExpenseFormDialogMeta.errorResponse &&
        createExpenseFormDialogMeta.errorResponse.response &&
        createExpenseFormDialogMeta.errorResponse.response.data &&
        createExpenseFormDialogMeta.errorResponse.response.data.errors
      ) {
        setHelperErrors(
          createExpenseFormDialogMeta.errorResponse.response.data.errors
        );
      } else {
        setHelperErrors(null);
      }
    },
  });

  const onCreateExpenseFormDialogSubmit = (
    values: TransactionFormInterface
  ) => {
    setCreateExpenseFormDialogMeta(submittingFormMetaData);

    handleCreateNewExpenseMutation.mutate(values);
  };

  const handleSnackbarClose = () => {
    setCreateExpenseFormDialogMeta(defaultFormMetaData);

    setHelperErrors(null);
  };

  return (
    <>
      <div className="level">
        <div className="level-left">
          <div className="level-item">
            <h1>Expenses Page</h1>
          </div>
        </div>
        <div className="level-right">
          <div className="level-item">
            <Button variant="outlined" onClick={openCreateExpenseFormDialog}>
              Add New
            </Button>
            <CreateTransactionFormDialog
              title="New Expense"
              isOpen={createExpenseFormDialogOpen}
              close={closeCreateExpenseFormDialog}
              formMeta={createExpenseFormDialogMeta}
              onSubmit={onCreateExpenseFormDialogSubmit}
              groupOptions={expenseGroups || []}
            />
          </div>
        </div>
      </div>
      <div className="data-table-container">
        <DataTable
          columns={columns}
          data={expenses || []}
          isLoading={isFetching}
          linearProgress={!isLoading}
        />
      </div>

      <CreateTransactionFormDialog
        title={updateFormTitle}
        isOpen={updateExpenseFormDialogOpen}
        close={closeUpdateExpenseFormDialog}
        formMeta={createExpenseFormDialogMeta}
        onSubmit={onUpdateExpenseFormDialogSubmit}
        groupOptions={expenseGroups || []}
        initialValues={{
          ...transactionObject,
          groupId: transactionObject.expenseGroupId || -1,
        }}
      />

      <ConfirmationDialog
        title={confirmationDialogTitle}
        isOpen={confirmationDialogOpen}
        handleYes={handleDeleteActionConfirmationYes}
        handleNo={handleDeleteActionConfirmationNo}
      />

      <MySnackbar
        isOpen={createExpenseFormDialogMeta.isSubmitted}
        handleSnackbarClose={handleSnackbarClose}
        severity={createExpenseFormDialogMeta.submitStatus}
        message={createExpenseFormDialogMeta.submitStatusMessage}
        errors={helperErrors}
      />
    </>
  );
}

export default ExpensesPage;
