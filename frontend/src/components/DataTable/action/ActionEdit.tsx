import { FormControlLabel, IconButton } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import { blue } from "@mui/material/colors";

type Props = {
  handleAction: React.MouseEventHandler<HTMLAnchorElement>;
};

function ActionEdit({ handleAction }: Props) {
  return (
    <FormControlLabel
      label=""
      className="mx-0"
      control={
        <IconButton
          color="secondary"
          aria-label="edit an item"
          onClick={handleAction}
          component="span"
        >
          <EditIcon style={{ color: blue[500] }} />
        </IconButton>
      }
    />
  );
}

export default ActionEdit;
