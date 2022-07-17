import LoginIcon from "@mui/icons-material/Login";
import PersonAddIcon from "@mui/icons-material/PersonAdd";

import { NavigationItemInterface } from "../models/layout/NavigationItemInterface";

export const accountNavigationItems: NavigationItemInterface[] = [
  {
    label: "Sign up",
    url: "/signup",
    className: "button is-primary has-text-weight-bold",
    icon: <LoginIcon />,
  },
  {
    label: "Log in",
    url: "/login",
    className: "button is-light",
    icon: <PersonAddIcon />,
  },
];
