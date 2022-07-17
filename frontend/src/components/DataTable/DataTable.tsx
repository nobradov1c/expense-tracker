import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { useState } from "react";
import { GroupInterface } from "../../data/models/GroupInterface";
import { ExpenseInterface } from "../../data/models/ExpenseInterface";
import { IncomeInterface } from "../../data/models/IncomeInterface";
import { LinearProgress } from "@mui/material";

type Props = {
  data: ExpenseInterface[] | IncomeInterface[] | GroupInterface[];
  columns: GridColDef[];
  isLoading?: boolean;
  linearProgress?: boolean;
};

function DataTable({
  data,
  columns,
  isLoading = false,
  linearProgress = true,
}: Props) {
  const [activePageSize, setActivePageSize] = useState(5);

  return (
    <>
      <DataGrid
        loading={isLoading}
        columns={columns}
        rows={data}
        pageSize={activePageSize}
        rowsPerPageOptions={[5, 10, 15]}
        onPageSizeChange={(newSize) => {
          setActivePageSize(newSize);
        }}
        components={
          linearProgress
            ? {
                LoadingOverlay: LinearProgress,
              }
            : {}
        }
      />
    </>
  );
}

export default DataTable;
