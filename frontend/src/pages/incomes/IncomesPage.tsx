import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { Button } from "@mui/material";

import DataTable from "../../components/DataTable/DataTable";
import incomesColumns from "../../components/DataTable/config/incomesColumns";
import {
  createNewIncome,
  deleteAnIncome,
  getAllIncomeGroups,
  getAllIncomes,
} from "../../services/incomesService";
import reactQueryConfig from "../../config/reactQueryConfig";
import { FormMetaInterface } from "../../components/form/config/FormMetaInterface";
import { AxiosError } from "axios";
import { TransactionFormInterface } from "../../models/forms/TransactionFormInterface";
import MySnackbar from "../../components/MySnackbar/MySnackbar";
import CreateTransactionFormDialog from "../../components/form/CreateTransactionFormDialog";

function IncomesPage() {
  const [createIncomeFormDialogOpen, setcreateIncomeFormDialogOpen] =
    useState<boolean>(false);
  const [createIncomeFormDialogMeta, setCreateIncomeFormDialogMeta] =
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

  const { data: incomeGroups } = useQuery(
    "incomeGroups",
    getAllIncomeGroups,
    reactQueryConfig
  );

  const {
    data: incomes,
    isFetching,
    isLoading,
  } = useQuery("incomes", getAllIncomes, reactQueryConfig);

  const openCreateIncomeFormDialog = () => {
    setcreateIncomeFormDialogOpen(true);
  };

  const closeCreateIncomeFormDialog = () => {
    setcreateIncomeFormDialogOpen(false);
  };

  const handleDetailsAction = (id: number) => {
    navigate(`/incomes/${id}`);
  };

  const handleEditAction = (id: number) => {
    // TODO: implement edit action
  };

  const handleDeleteAnIncomeMutation = useMutation(deleteAnIncome, {
    onSuccess: () => {
      queryClient.invalidateQueries("incomes");
    },
  });

  const handleDeleteAction = async function (id: number) {
    handleDeleteAnIncomeMutation.mutate(id);
  };

  const columns = incomesColumns({
    handleEditAction,
    handleDetailsAction,
    handleDeleteAction,
  });

  const handleCreateNewIncomeMutation = useMutation(createNewIncome, {
    onSuccess: () => {
      queryClient.invalidateQueries("incomes");

      setCreateIncomeFormDialogMeta({
        submitStatus: "success",
        submitStatusMessage: "Income created successfully",
        isSubmitting: false,
        isSubmitted: true,
        errorResponse: null,
      });

      setcreateIncomeFormDialogOpen(false);
    },
    onError: (error: AxiosError) => {
      setCreateIncomeFormDialogMeta({
        submitStatus: "error",
        submitStatusMessage: "Error creating income",
        isSubmitting: false,
        isSubmitted: true,
        errorResponse: error,
      });

      if (
        createIncomeFormDialogMeta.errorResponse &&
        createIncomeFormDialogMeta.errorResponse.response &&
        createIncomeFormDialogMeta.errorResponse.response.data &&
        createIncomeFormDialogMeta.errorResponse.response.data.errors
      ) {
        setHelperErrors(
          createIncomeFormDialogMeta.errorResponse.response.data.errors
        );
      } else {
        setHelperErrors(null);
      }
    },
  });

  const onCreateIncomeFormDialogSubmit = (values: TransactionFormInterface) => {
    setCreateIncomeFormDialogMeta({
      isSubmitted: false,
      isSubmitting: true,
      submitStatus: undefined,
      submitStatusMessage: null,
      errorResponse: null,
    });

    handleCreateNewIncomeMutation.mutate(values);
  };

  const handleSnackbarClose = () => {
    setCreateIncomeFormDialogMeta({
      ...createIncomeFormDialogMeta,
      isSubmitted: false,
    });

    setHelperErrors(null);
  };

  return (
    <>
      <div className="level">
        <div className="level-left">
          <div className="level-item">
            <h1>Incomes Page</h1>
          </div>
        </div>
        <div className="level-right">
          <div className="level-item">
            <Button variant="outlined" onClick={openCreateIncomeFormDialog}>
              Add New
            </Button>
            <CreateTransactionFormDialog
              title="New Income"
              isOpen={createIncomeFormDialogOpen}
              close={closeCreateIncomeFormDialog}
              formMeta={createIncomeFormDialogMeta}
              onSubmit={onCreateIncomeFormDialogSubmit}
              groupOptions={incomeGroups || []}
            />
          </div>
        </div>
      </div>

      <div className="data-table-container">
        <DataTable
          columns={columns}
          data={incomes || []}
          isLoading={isFetching}
          linearProgress={!isLoading}
        />
      </div>

      <MySnackbar
        isOpen={createIncomeFormDialogMeta.isSubmitted}
        handleSnackbarClose={handleSnackbarClose}
        severity={createIncomeFormDialogMeta.submitStatus}
        message={createIncomeFormDialogMeta.submitStatusMessage}
        errors={helperErrors}
      />
    </>
  );
}

export default IncomesPage;
