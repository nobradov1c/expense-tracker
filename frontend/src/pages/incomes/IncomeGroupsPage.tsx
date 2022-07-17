import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";

import DataTable from "../../components/DataTable/DataTable";
import incomeGroupsColumns from "../../components/DataTable/config/incomeGroupsColumns";
import {
  createNewIncomeGroup,
  deleteAnIncomeGroup,
  getAllIncomeGroups,
} from "../../services/incomesService";
import { useMutation, useQuery, useQueryClient } from "react-query";
import reactQueryConfig from "../../config/reactQueryConfig";
import { FormMetaInterface } from "../../components/form/config/FormMetaInterface";
import { AxiosError } from "axios";
import { GroupTypeFormInterface } from "../../models/forms/GroupTypeFormInterface";
import MySnackbar from "../../components/MySnackbar/MySnackbar";
import CreateGroupFormDialog from "../../components/form/CreateGroupFormDialog";

function IncomeGroupsPage() {
  const [createIncomeGroupFormDialogOpen, setCreateIncomeGroupFormDialogOpen] =
    useState<boolean>(false);
  const [createIncomeGroupFormDialogMeta, setCreateIncomeGroupFormDialogMeta] =
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

  const {
    data: incomeGroups,
    isFetching,
    isLoading,
  } = useQuery("incomeGroups", getAllIncomeGroups, reactQueryConfig);

  const openCreateIncomeGroupFormDialog = () => {
    setCreateIncomeGroupFormDialogOpen(true);
  };

  const closeCreateIncomeGroupFormDialog = () => {
    setCreateIncomeGroupFormDialogOpen(false);
  };

  const handleDetailsAction = (id: number) => {
    navigate(`/income-groups/${id}`);
  };

  const handleEditAction = (id: number) => {
    // TODO: implement edit action
  };

  const handleDeleteAnIncomeGroupMutation = useMutation(deleteAnIncomeGroup, {
    onSuccess: () => {
      queryClient.invalidateQueries("incomeGroups");
      queryClient.invalidateQueries("incomes");
    },
  });

  const handleDeleteAction = (id: number) => {
    handleDeleteAnIncomeGroupMutation.mutate(id);
  };

  const columns = incomeGroupsColumns({
    handleEditAction,
    handleDetailsAction,
    handleDeleteAction,
  });

  const handleCreateNewIncomeGroupMutation = useMutation(createNewIncomeGroup, {
    onSuccess: () => {
      queryClient.invalidateQueries("incomeGroups");

      setCreateIncomeGroupFormDialogMeta({
        submitStatus: "success",
        submitStatusMessage: "Expense group created successfully",
        isSubmitting: false,
        isSubmitted: true,
        errorResponse: null,
      });

      setCreateIncomeGroupFormDialogOpen(false);
    },

    onError: (error: AxiosError) => {
      setCreateIncomeGroupFormDialogMeta({
        submitStatus: "error",
        submitStatusMessage: "Error creating expense group",
        isSubmitting: false,
        isSubmitted: true,
        errorResponse: error,
      });

      if (
        createIncomeGroupFormDialogMeta.errorResponse &&
        createIncomeGroupFormDialogMeta.errorResponse.response &&
        createIncomeGroupFormDialogMeta.errorResponse.response.data &&
        createIncomeGroupFormDialogMeta.errorResponse.response.data.errors
      ) {
        setHelperErrors(
          createIncomeGroupFormDialogMeta.errorResponse.response.data.errors
        );
      } else {
        setHelperErrors(null);
      }
    },
  });

  const handleCreateIncomeGroupFormSubmit = (
    values: GroupTypeFormInterface
  ) => {
    setCreateIncomeGroupFormDialogMeta({
      isSubmitted: false,
      isSubmitting: true,
      submitStatus: undefined,
      submitStatusMessage: null,
      errorResponse: null,
    });

    handleCreateNewIncomeGroupMutation.mutate(values);
  };

  const handleSnackbarClose = () => {
    setCreateIncomeGroupFormDialogMeta({
      ...createIncomeGroupFormDialogMeta,
      isSubmitted: false,
    });

    setHelperErrors(null);
  };

  return (
    <>
      <div className="level">
        <div className="level-left">
          <div className="level-item">
            <h1>Income Groups Page</h1>
          </div>
        </div>
        <div className="level-right">
          <div className="level-item">
            <Button
              variant="outlined"
              onClick={openCreateIncomeGroupFormDialog}
            >
              Add New
            </Button>
            <CreateGroupFormDialog
              title="New Income Group"
              isOpen={createIncomeGroupFormDialogOpen}
              close={closeCreateIncomeGroupFormDialog}
              formMeta={createIncomeGroupFormDialogMeta}
              onSubmit={handleCreateIncomeGroupFormSubmit}
            />
          </div>
        </div>
      </div>

      <div className="data-table-container">
        <DataTable
          columns={columns}
          data={incomeGroups || []}
          isLoading={isFetching}
          linearProgress={!isLoading}
        />
      </div>

      <MySnackbar
        isOpen={createIncomeGroupFormDialogMeta.isSubmitted}
        handleSnackbarClose={handleSnackbarClose}
        severity={createIncomeGroupFormDialogMeta.submitStatus}
        message={createIncomeGroupFormDialogMeta.submitStatusMessage}
        errors={helperErrors}
      />
    </>
  );
}

export default IncomeGroupsPage;
