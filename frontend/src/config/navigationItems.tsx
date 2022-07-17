import AddBoxIcon from "@mui/icons-material/AddBox";
import AddIcon from "@mui/icons-material/Add";
import HomeIcon from "@mui/icons-material/Home";
import IndeterminateCheckBoxIcon from "@mui/icons-material/IndeterminateCheckBox";
import RemoveIcon from "@mui/icons-material/Remove";

import { NavigationItemInterface } from "../models/layout/NavigationItemInterface";

export const navigationItems: NavigationItemInterface[] = [
  {
    label: "Home",
    url: "/",
    icon: <HomeIcon />,
  },
  {
    label: "Expenses",
    url: "/expenses",
    icon: <RemoveIcon />,
  },
  {
    label: "Expense Groups",
    url: "/expense-groups",
    icon: <IndeterminateCheckBoxIcon />,
  },
  {
    label: "Incomes",
    url: "/incomes",
    icon: <AddIcon />,
  },
  {
    label: "Income Groups",
    url: "/income-groups",
    icon: <AddBoxIcon />,
  },
];
