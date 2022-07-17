import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
} from "@mui/material";

type Props = {
  title: string;
  isOpen: boolean;
  handleNo: () => void;
  handleYes: () => void;
};

function ConfirmationDialog({
  title,
  isOpen = false,
  handleNo,
  handleYes,
}: Props) {
  return (
    <Dialog open={isOpen} onClose={handleNo}>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent className="pt-4">
        <DialogActions>
          <Button onClick={handleNo}>No</Button>
          <Button onClick={handleYes}>Yes</Button>
        </DialogActions>
      </DialogContent>
    </Dialog>
  );
}

export default ConfirmationDialog;
