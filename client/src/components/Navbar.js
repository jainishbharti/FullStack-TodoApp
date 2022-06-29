import React, { useContext } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { NavLink } from "react-router-dom";
import { UserContext } from "../context/UserContext";
import { LOGOUT } from "../context/constants/ActionConstants";

const Navbar = () => {
  const { state, dispatch } = useContext(UserContext);

  const handleLogout = async () => {
    localStorage.removeItem("user");
    dispatch({ type: LOGOUT });
  };


  const { isLoggedIn } = state;
  
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar
        position="static"
        style={{ background: "transparent", boxShadow: "none" }}
      >
        <Toolbar>
          <Typography
            component="div"
            sx={{
              flexGrow: 1,
              fontFamily: "Shadows Into Light",
              fontWeight: "550",
            }}
          >
            <NavLink to="/" className="logo-link">
              Add Todos Here
            </NavLink>
          </Typography>

          {!isLoggedIn ? (
            <>
              <Button color="inherit">
                <NavLink to="/login" className="nav-link">
                  Login
                </NavLink>
              </Button>
              <Button color="inherit">
                <NavLink to="/register" className="nav-link">
                  SIGN UP
                </NavLink>
              </Button>
            </>
          ) : (
            <>
              <Button color="secondary" >
               <span className="nav-link">Hello, {state.name.split(" ")[0]}</span>
              </Button>
              <Button
                color="inherit"
                onClick={() => handleLogout()}
                className="nav-link"
              >
                <span className="nav-link">LOGOUT</span>
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
};

export default Navbar;
