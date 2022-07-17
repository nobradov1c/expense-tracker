import {
  Box,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import { navigationItems } from "../../../config/navigationItems";
import { accountNavigationItems } from "../../../config/accountNavigationItems";
import { useNavigate } from "react-router-dom";
import React from "react";

function MobileNavigationDrawer({
  hamburgerMenuIsOpen,
  toggleHamburgerMenu,
}: {
  hamburgerMenuIsOpen: boolean;
  toggleHamburgerMenu: (
    newState: boolean
  ) => (event: React.KeyboardEvent | React.MouseEvent) => void;
}) {
  const navigate = useNavigate();

  const handleLinkClick = (newPath: string) => (event: React.MouseEvent) => {
    toggleHamburgerMenu(false);
    navigate(newPath);
  };

  return (
    <Drawer
      anchor="right"
      open={hamburgerMenuIsOpen}
      onClose={toggleHamburgerMenu(false)}
    >
      <Box
        role="presentation"
        onClick={toggleHamburgerMenu(false)}
        onKeyDown={toggleHamburgerMenu(false)}
      >
        <List>
          {navigationItems.map((item, index) => (
            <ListItem key={`${item.label}-${index}`} disablePadding>
              <ListItemButton onClick={handleLinkClick(item.url)}>
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.label} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
        <Divider />
        <List>
          {accountNavigationItems.map((item, index) => (
            <ListItem key={`${item.label}-${index}`} disablePadding>
              <ListItemButton onClick={handleLinkClick(item.url)}>
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.label} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      </Box>
    </Drawer>
  );
}

export default MobileNavigationDrawer;
