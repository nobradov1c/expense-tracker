import { FormControlLabel, IconButton } from "@mui/material";
import ArrowCircleRightIcon from "@mui/icons-material/ArrowCircleRight";
import { green } from "@mui/material/colors";

type Props = {
  handleAction: React.MouseEventHandler<HTMLAnchorElement>;
};

function ActionDetails({ handleAction }: Props) {
  return (
    <FormControlLabel
      label=""
      className="mx-0"
      control={
        <IconButton
          color="secondary"
          aria-label="details"
          onClick={handleAction}
          component="span"
        >
          <ArrowCircleRightIcon style={{ color: green[500] }} />
        </IconButton>
      }
    />
  );
}

export default ActionDetails;
