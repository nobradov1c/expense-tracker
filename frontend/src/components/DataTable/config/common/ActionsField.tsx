import { GridRenderCellParams } from "@mui/x-data-grid";
import ActionDelete from "../../action/ActionDelete";
import ActionDetails from "../../action/ActionDetails";
import ActionEdit from "../../action/ActionEdit";

type Props = {
  params: GridRenderCellParams<any, any, any>;
  handleEditAction: (id: number) => void;
  handleDetailsAction: (id: number) => void;
  handleDeleteAction: (id: number) => void;
};

function ActionsField({
  params,
  handleEditAction,
  handleDetailsAction,
  handleDeleteAction,
}: Props) {
  return (
    <div
      className="data-action-field-centered level is-mobile is-flex-grow-1 is-justify-content-center is-align-items-center"
    >
      <ActionEdit
        handleAction={(e, id = params.row.id) => {
          handleEditAction(id);
        }}
      />
      <ActionDetails
        handleAction={(e, id = params.row.id) => {
          handleDetailsAction(id);
        }}
      />
      <ActionDelete
        handleAction={(e, id = params.row.id) => {
          handleDeleteAction(id);
        }}
      />
    </div>
  );
}

export default ActionsField;
