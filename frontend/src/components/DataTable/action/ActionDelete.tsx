import { FormControlLabel, IconButton } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { red } from "@mui/material/colors";

type Props = {
  handleAction: React.MouseEventHandler<HTMLAnchorElement>;
};

function ActionDelete({ handleAction }: Props) {
  return (
    <FormControlLabel
      label=""
      className="mx-0"
      control={
        <IconButton
          color="secondary"
          aria-label="delete an item"
          onClick={handleAction}
          component="span"
        >
          <DeleteIcon style={{ color: red[500] }} />
        </IconButton>
      }
    />
  );
}

export default ActionDelete;
