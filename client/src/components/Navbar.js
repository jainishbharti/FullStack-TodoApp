import React, { useContext, useState } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Drawer from "@mui/material/Drawer";
import Button from "@mui/material/Button";
import { Link } from "react-router-dom";
import List from "@mui/material/List";
import Toolbar from "@mui/material/Toolbar";
import AddTaskIcon from "@mui/icons-material/AddTask";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import InboxIcon from "@mui/icons-material/MoveToInbox";
import MailIcon from "@mui/icons-material/Mail";
import { NavLink } from "react-router-dom";
import { UserContext } from "../context/UserContext";
import { LOGOUT } from "../context/constants/ActionConstants";
import LogoutIcon from "@mui/icons-material/Logout";
import { Typography } from "@mui/material";

const Navbar = () => {
  const { state, dispatch } = useContext(UserContext);
  const [drawer, setDrawer] = useState(false);

  const handleDrawerOpen = () => {
    setDrawer(true);
  };
  const handleDrawerClose = () => {
    setDrawer(false);
  };

  const loggedInMenuItems = [
    { id: 1, title: "About", href: "#" },
    { id: 2, title: "Profile", href: "/" },
    { id: 3, title: "Todos", href: "/" }
  ];
  const guestMenuItems = [
    { id: 1, title: "About", href: "#" },
    { id: 2, title: "Login", href: "/login" },
    { id: 3, title: "Register", href: "/register" },
  ];

  const list = () => (
    <Box
      sx={{ width: 250, marginTop: "1rem" }}
      role="presentation"
      onClick={handleDrawerClose}
      onKeyDown={handleDrawerClose}
    >
      <List>
        <NavLink to="/" className="logo-link">
          <IconButton>
            <AddTaskIcon
              fontSize="large"
              color="warning"
              sx={{ mr: 2, mb: 4 }}
            />
            <Typography
              fontSize="large"
              color="warning"
              sx={{ mr: 2, mb: 4, fontWeight: 550, color: "secondary" }}
            >
              <span>TODO</span>
            </Typography>
          </IconButton>
        </NavLink>
        {isLoggedIn
          ? loggedInMenuItems.map(({ title, href, id }) => (
              <ListItem key={id} disablePadding>
                <Link to={href} style={{textDecoration: "none" , color: "#424242"}}>
                  <ListItemButton>
                    <ListItemIcon>
                      {id % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                    </ListItemIcon>
                      <ListItemText primary={title} />
                  </ListItemButton>
                </Link>
              </ListItem>
            ))
          : guestMenuItems.map(({ title, href, id }) => (
              <ListItem key={id} disablePadding>
                <Link to={href} style={{textDecoration: "none", color: "#424242"}}>
                  <ListItemButton>
                    <ListItemIcon>
                      {id % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                    </ListItemIcon>
                      <ListItemText primary={title}/>
                  </ListItemButton>
                </Link>
              </ListItem>
            ))}
      </List>
    </Box>
  );

  const handleLogout = async () => {
    localStorage.removeItem("user");
    dispatch({ type: LOGOUT });
  };

  const { isLoggedIn } = state;

  return (
    <>
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static">
          <Toolbar className="flex">
            <div>
              <IconButton
                size="large"
                edge="start"
                color="inherit"
                aria-label="menu"
                onClick={handleDrawerOpen}
              >
                <MenuIcon />
              </IconButton>
              <NavLink to="/" className="logo-link">
                <IconButton color="inherit">
                  <AddTaskIcon fontSize="large" sx={{ mr: 2 }} />
                </IconButton>
              </NavLink>
            </div>

            {!isLoggedIn ? (
              <div>
                <NavLink to="/login" className="nav-link">
                  <Button color="success" variant="contained">
                    <span className="nav-action">Sign in</span>
                  </Button>
                </NavLink>

                <NavLink to="/register" className="nav-link">
                  <Button color="warning" variant="contained">
                    <span className="nav-action">Sign Up</span>
                  </Button>
                </NavLink>
              </div>
            ) : (
              <>
                <Button color="inherit">
                  <Typography color="inherit" sx={{ fontWeight: 550 }} component="span">
                    Hello, {state.name.split(" ")[0]}
                  </Typography>
                </Button>
                <Button color="inherit" onClick={() => handleLogout()}>
                  <Typography color="inherit" sx={{ fontWeight: 550 }} component="span">
                    
                      <LogoutIcon size="large" />
                    
                  </Typography>
                </Button>
              </>
            )}
          </Toolbar>
        </AppBar>
      </Box>
      <Drawer open={drawer} onClose={handleDrawerClose}>
        {list()}
      </Drawer>
    </>
  );
};

export default Navbar;
