import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Button } from "@mui/material";

import DataTable from "../../components/DataTable/DataTable";
import expensesColumns from "../../components/DataTable/config/expensesColumns";
import {
  createNewExpense,
  deleteAnExpense,
  getAllExpenseGroups,
  getAllExpenses,
} from "../../services/expensesService";
import reactQueryConfig from "../../config/reactQueryConfig";
import { FormMetaInterface } from "../../components/form/config/FormMetaInterface";
import { AxiosError } from "axios";
import { TransactionFormInterface } from "../../models/forms/TransactionFormInterface";
import MySnackbar from "../../components/MySnackbar/MySnackbar";
import CreateTransactionFormDialog from "../../components/form/CreateTransactionFormDialog";

function ExpensesPage() {
  const [createExpenseFormDialogOpen, setCreateExpenseFormDialogOpen] =
    useState<boolean>(false);
  const [createExpenseFormDialogMeta, setCreateExpenseFormDialogMeta] =
    useState<FormMetaInterface>({
      isSubmitting: false,
      isSubmitted: false,
      submitStatus: undefined,
      submitStatusMessage: null,
      errorResponse: null,
    });
  const [helperErrors, setHelperErrors] = useState<string[] | null>(null);
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: expenseGroups } = useQuery(
    "expenseGroups",
    getAllExpenseGroups,
    reactQueryConfig
  );

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

  const handleDetailsAction = (id: number) => {
    navigate(`/expenses/${id}`);
  };

  const handleEditAction = (id: number) => {
    // TODO: implement edit action
  };

  const handleDeleteAnExpenseMutation = useMutation(deleteAnExpense, {
    onSuccess: () => {
      queryClient.invalidateQueries("expenses");
    },
  });

  const handleDeleteAction = (id: number) => {
    handleDeleteAnExpenseMutation.mutate(id);
  };

  const columns = expensesColumns({
    handleEditAction,
    handleDetailsAction,
    handleDeleteAction,
  });

  const handleCreateNewExpenseMutation = useMutation(createNewExpense, {
    onSuccess: () => {
      queryClient.invalidateQueries("expenses");

      setCreateExpenseFormDialogMeta({
        submitStatus: "success",
        submitStatusMessage: "Expense created successfully",
        isSubmitting: false,
        isSubmitted: true,
        errorResponse: null,
      });

      // close form dialog
      setCreateExpenseFormDialogOpen(false);
    },

    onError: (error: AxiosError) => {
      setCreateExpenseFormDialogMeta({
        submitStatus: "error",
        submitStatusMessage: "Error creating expense",
        errorResponse: error,
        isSubmitting: false,
        isSubmitted: true,
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
    setCreateExpenseFormDialogMeta({
      isSubmitted: false,
      isSubmitting: true,
      submitStatus: undefined,
      submitStatusMessage: null,
      errorResponse: null,
    });

    handleCreateNewExpenseMutation.mutate(values);
  };

  const handleSnackbarClose = () => {
    setCreateExpenseFormDialogMeta({
      ...createExpenseFormDialogMeta,
      isSubmitted: false,
    });

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
