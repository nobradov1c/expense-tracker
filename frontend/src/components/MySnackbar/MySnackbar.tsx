import {
  Alert,
  List,
  ListItem,
  ListItemText,
  ListSubheader,
  Snackbar,
} from "@mui/material";
import { MySnackbarInterface } from "./MySnackbarInterface";

function MySnackbar({
  isOpen,
  handleSnackbarClose,
  severity,
  message,
  errors = null,
}: MySnackbarInterface & { handleSnackbarClose: () => void }) {
  if (message === null) {
    message = "Unknown status message";
  }

  return (
    <Snackbar
      open={isOpen}
      autoHideDuration={6000}
      onClose={handleSnackbarClose}
      anchorOrigin={{ vertical: "top", horizontal: "right" }}
    >
      <Alert
        onClose={handleSnackbarClose}
        severity={severity}
        sx={{ width: "100%" }}
      >
        {!errors && message}
        {errors && (
          <List
            subheader={<ListSubheader component="div">{message}</ListSubheader>}
          >
            {errors.map((error: string, index: number) => (
              <ListItem key={`${index}-${error}`}>
                <ListItemText primary={error} />
              </ListItem>
            ))}
          </List>
        )}
      </Alert>
    </Snackbar>
  );
}

export default MySnackbar;
