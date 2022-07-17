import { GridColDef, GridValueGetterParams } from "@mui/x-data-grid";
import ActionsField from "./ActionsField";

type Props = {
  handleEditAction: (id: number) => void;
  handleDetailsAction: (id: number) => void;
  handleDeleteAction: (id: number) => void;
};

export default function transactionColumns({
  handleEditAction,
  handleDetailsAction,
  handleDeleteAction,
}: Props): GridColDef[] {
  return [
    { field: "id", headerName: "ID", width: 70, flex: 1 },
    { field: "description", headerName: "Description", width: 130, flex: 1 },
    {
      field: "amount",
      headerName: "Amount",
      type: "number",
      width: 90,
      flex: 1,
    },
    {
      field: "expenseGroupId",
      headerName: "Expense Group ID",
      type: "number",
      width: 90,
      valueGetter: (params: GridValueGetterParams) =>
        `${params.row.expenseGroupId || "none"}`,
      flex: 1,
    },
    {
      field: "actions",
      headerName: "Actions",
      sortable: false,
      flex: 1,
      maxWidth: 160,
      renderCell: (params) => {
        return ActionsField({
          params,
          handleEditAction,
          handleDetailsAction,
          handleDeleteAction,
        });
      },
    },
  ];
}
