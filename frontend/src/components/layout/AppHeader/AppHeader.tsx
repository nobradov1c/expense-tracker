import { Typography } from "@mui/material";

import { useState } from "react";
import { Link, NavLink } from "react-router-dom";

import "./AppHeader.sass";
import { navigationItems } from "../../../config/navigationItems";
import MobileNavigationDrawer from "../MobileNavigationDrawer/MobileNavigationDrawer";
import { accountNavigationItems } from "../../../config/accountNavigationItems";

function AppHeader() {
  const [hamburgerMenuIsOpen, setHamburgerMenuState] = useState(false);

  const toggleHamburgerMenu =
    (newState: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => {
      if (
        event.type === "keydown" &&
        ((event as React.KeyboardEvent).key === "Tab" ||
          (event as React.KeyboardEvent).key === "Shift")
      ) {
        return;
      }

      setHamburgerMenuState(newState);
    };

  return (
    <nav
      className="navbar app-header has-background-black-ter has-text-white"
      role="navigation"
      aria-label="main navigation"
    >
      <div className="navbar-brand is-align-items-center">
        <Link className="navbar-item" to="/">
          <Typography variant="h6" noWrap sx={{ flexGrow: 1 }}>
            Expense Tracker
          </Typography>
        </Link>

        <button
          className="navbar-burger has-text-white"
          aria-label="menu"
          aria-expanded="false"
          data-target="navbarBasicExample"
          onClick={toggleHamburgerMenu(true)}
        >
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
        </button>
      </div>

      <div id="navbarBasicExample" className="navbar-menu">
        <div className="navbar-start">
          {navigationItems.map((item) => (
            <NavLink className="navbar-item" key={item.url} to={item.url}>
              {item.label}
            </NavLink>
          ))}
        </div>

        <div className="navbar-end">
          <div className="navbar-item">
            <div className="buttons">
              {accountNavigationItems.map((item) => (
                <Link
                  key={item.url}
                  to={item.url}
                  className={item.className ? item.className : ""}
                >
                  {item.label}
                </Link>
              ))}
            </div>
          </div>
        </div>
      </div>

      <MobileNavigationDrawer
        hamburgerMenuIsOpen={hamburgerMenuIsOpen}
        toggleHamburgerMenu={toggleHamburgerMenu}
      />
    </nav>
  );
}

export default AppHeader;
