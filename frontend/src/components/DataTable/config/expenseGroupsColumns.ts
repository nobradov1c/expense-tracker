import { GridColDef } from "@mui/x-data-grid";
import ActionsField from "./common/ActionsField";

type Props = {
  handleEditAction: (id: number) => void;
  handleDetailsAction: (id: number) => void;
  handleDeleteAction: (id: number) => void;
};

export default function expenseGroupsColumns({
  handleEditAction,
  handleDetailsAction,
  handleDeleteAction,
}: Props): GridColDef[] {
  return [
    { field: "id", headerName: "ID", width: 70, flex: 1 },
    { field: "name", headerName: "Name", width: 130, flex: 1 },
    { field: "description", headerName: "Description", width: 130, flex: 1 },
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
