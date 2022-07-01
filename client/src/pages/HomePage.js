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
import AddTaskIcon from "@mui/icons-material/AddTask";
import IconButton from "@mui/material/IconButton";
import { DemoTodo, Item } from "../components/DemoTodo";
import { Divider, Grid, Typography } from "@mui/material";
import { Box } from "@mui/system";

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
      const token = localStorage.getItem("user");
      if (token) {
        const { status, data } = await axios.get("/auth/checkLogin", {
          headers: {
            Authorization: `Basic ${localStorage.getItem("user")}`,
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
      } else {
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
        Authorization: `Basic ${localStorage.getItem("user")}`,
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
          <Container maxWidth="sm">
            <Box
              p={2}
              mb={0.5}
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
              }}
            >
              <IconButton>
                <AddTaskIcon fontSize="large" color="warning" sx={{ mr: 2 }} />
                <Typography
                  fontSize="large"
                  color="warning"
                  sx={{ mr: 2, fontWeight: 550 }}
                >
                  <span>Todo List</span>
                </Typography>
              </IconButton>
              <Typography
                component="h5"
                sx={{fontStyle: "italic" }}
              >
                <span>Helps you keep precise and organized !</span>
              </Typography>
            </Box>
            

            <Box sx={{ marginTop: "5rem" }}>
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
                  color="primary"
                  variant="outlined"
                  sx={{
                    color: "black",
                    marginBottom: "1rem",
                    width: "100%",
                    marginRight: "1rem",
                  }}
                  placeholder="What's on your mind today?"
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
            </Box>

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
          <Container>
            <DemoTodo />
          </Container>
        </div>
      )}
    </div>
  );
};

export default HomePage;
