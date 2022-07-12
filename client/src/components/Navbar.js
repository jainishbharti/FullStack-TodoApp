import React, { useContext, useState, useEffect } from "react";
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

function getWindowSize() {
  const { innerWidth, innerHeight } = window;
  return { innerWidth, innerHeight };
}

const Navbar = () => {
  const [windowSize, setWindowSize] = useState(getWindowSize());
  const { state, dispatch } = useContext(UserContext);
  const [drawer, setDrawer] = useState(false);

  useEffect(() => {
    function handleWindowResize() {
      setWindowSize(getWindowSize());
    }

    window.addEventListener("resize", handleWindowResize);

    return () => {
      window.removeEventListener("resize", handleWindowResize);
    };
  }, []);

  const handleDrawerOpen = () => {
    setDrawer(true);
  };
  const handleDrawerClose = () => {
    setDrawer(false);
  };

  const loggedInMenuItems = [
    { id: 1, title: "About", href: "#" },
    { id: 2, title: "Profile", href: "/" },
    { id: 3, title: "Todos", href: "/" },
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
                <Link
                  to={href}
                  style={{ textDecoration: "none", color: "#424242" }}
                >
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
                <Link
                  to={href}
                  style={{ textDecoration: "none", color: "#424242" }}
                >
                  <ListItemButton>
                    <ListItemIcon>
                      {id % 2 === 0 ? <InboxIcon /> : <MailIcon />}
                    </ListItemIcon>
                    <ListItemText primary={title} />
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
              {windowSize.innerWidth <= 500 ? (
                <div data-testid="hamburger">
                  <IconButton
                    size="large"
                    edge="start"
                    color="inherit"
                    aria-label="menu"
                    onClick={handleDrawerOpen}
                  >
                    <MenuIcon />
                  </IconButton>
                </div>
              ) : null}
              <NavLink to="/" className="logo-link">
                <IconButton color="inherit">
                  <AddTaskIcon fontSize="large" sx={{ mr: 2 }} />
                </IconButton>
              </NavLink>
            </div>

            {!isLoggedIn ? (
              windowSize.innerWidth >= 500 ? (
                <div>
                  <NavLink to="/login" className="nav-link">
                    <Button color="success" variant="contained">
                      <span className="nav-action">Login</span>
                    </Button>
                  </NavLink>

                  <NavLink to="/register" className="nav-link">
                    <Button color="warning" variant="contained">
                      <span className="nav-action">SignUp</span>
                    </Button>
                  </NavLink>
                </div>
              ) : null
            ) : (
              <>
                <Button color="inherit">
                  <Typography
                    color="inherit"
                    sx={{ fontWeight: 550 }}
                    component="span"
                  >
                    Hello, {state.name.split(" ")[0]}
                  </Typography>
                </Button>
                <Button
                  role="logoutButton"
                  color="inherit"
                  onClick={() => handleLogout()}
                >
                  <Typography
                    color="inherit"
                    sx={{ fontWeight: 550 }}
                    component="span"
                  >
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
