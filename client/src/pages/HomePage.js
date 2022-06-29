import React, { useContext, useEffect, useState } from "react";
import TodoCard from "../components/TodoCard";
import Container from "@mui/material/Container";
import { UserContext } from "../context/UserContext";
import TextField from "@mui/material/Input";
import Checkbox from "@mui/material/Checkbox";
import {
  ADD_TODO,
  GET_TODOS,
  IS_LOGGED,
} from "../context/constants/ActionConstants";
import axios from "axios";
import Button from "@mui/material/Button";
import AddIcon from "@mui/icons-material/Add";
import { Link } from "react-router-dom";

const HomePage = () => {
  const { state, dispatch } = useContext(UserContext);
  const [user, setUser] = useState({});

  const fetchTodos = async ({ userId }) => {
    const { data } = await axios.get(`/todos/user/${userId}`, {
      headers: { Authorization: `Basic ${localStorage.getItem("user")}` },
    });
    dispatch({ type: GET_TODOS, payload: data });
  };

  useEffect(() => {
    const checkIfLoggedIn = async () => {
      const { status, data } = await axios.get("/auth/checkLogin", {
        'headers': {
          'Authorization': `Basic ${localStorage.getItem("user")}`,
          "Content-Type": "application/json",
        },
      });
      if (status === 202) {
        setUser(data);
        fetchTodos(data);
        dispatch({ type: IS_LOGGED, payload: data });
      } else if (status === 401) {
        console.log("User unauthorized!");
      }
    };
    checkIfLoggedIn();
  }, []);

  const handleAdd = async (e) => {
    e.preventDefault();
    const todo = {
      title: e.target.title.value,
      done: e.target.done.checked,
      user,
    };
    const { data } = await axios.post("/todos/", todo, {
      headers: {
        "Content-Type": "application/json",
        'Authorization': `Basic ${localStorage.getItem("user")}`,
      },
    });
    dispatch({ type: ADD_TODO, payload: data });
    e.target.reset();
    fetchTodos(user);
  };

  return (
    <div>
      {state.isLoggedIn ? (
        <div>
          <h1 className="heading">Welcome, {user ? user.name : "User"}</h1>
          <Container maxWidth="sm">
            <form onSubmit={(e) => handleAdd(e)} className="card-content">
              <Checkbox
                sx={{
                  color: "black",
                  marginBottom: "1rem",
                  width: "20px",
                  marginRight: "1em",
                }}
                name="done"
              />
              <TextField
                variant="outlined"
                sx={{
                  color: "black",
                  marginBottom: "1rem",
                  width: "100%",
                  marginRight: "1rem",
                }}
                name="title"
              />

              <Button
                type="submit"
                color="success"
                variant="contained"
                className="add-btn"
              >
                <AddIcon />
              </Button>
            </form>

            {state.todos &&
              state.todos.map((todo) => (
                <TodoCard
                  key={todo.todoId}
                  todoId={todo.todoId}
                  title={todo.title}
                  done={todo.done}
                  dispatch={dispatch}
                />
              ))}
          </Container>
        </div>
      ) : (
        <div>
          <h1 className="heading">Welcome, User</h1>
          <Container>
            <h1 className="heading">
              Create Todo's List for Yourself !! Just By{" "}
              <Link role="loginPageLink" to="/login" className="link">
                Logging In
              </Link>
            </h1>
          </Container>
        </div>
      )}
    </div>
  );
};

export default HomePage;
