import React, { useContext, useEffect } from "react";
import TodoCard from "../components/TodoCard";
import Container from "@mui/material/Container";
import { UserContext } from "../context/UserContext";
import TextField from "@mui/material/Input";
import Checkbox from '@mui/material/Checkbox';
import {
  ADD_TODO,
  GET_TODOS,
  IS_LOGGED,
} from "../context/constants/ActionConstants";
import axios from "axios";
import Button from "@mui/material/Button";
import AddIcon from "@mui/icons-material/Add";
import { Link } from "react-router-dom";

axios.defaults.baseURL = "http://localhost:8080/todo/api/";

const HomePage = () => {
  const { state, dispatch } = useContext(UserContext);

  useEffect(() => {
    const fetchTodos = async () => {
      const { data } = await axios.get("http://localhost:8080/todo/api/todos/", {
        headers: { 'Authorization': `Basic ${localStorage.getItem("user")}` },
      });
      dispatch({ type: GET_TODOS, payload: data });
    };
    fetchTodos();

    const checkIfLoggedIn = async () => {
      const { status, data } = await axios.get(
        "http://localhost:8080/todo/api/auth/checkLogin",
        {
          headers: { "Authorization": `Basic ${localStorage.getItem("user")}` },
        }
      );
      if (status === 200) {
        console.log(data);
        dispatch({ type: IS_LOGGED, payload: data });
      } else if(status === 401){
        console.log('User unauthorized: ' );  
      }
    };
    checkIfLoggedIn();
  }, [dispatch]);

  const handleAdd = async (e) => {
    e.preventDefault();
    const { name, value } = e.target;
    const todo = {
      [name]: value,
      done: false,
      user: {
        name: "John Doe",
        email: "johnny@gmail.com",
        password: "Hexagon",
      },
    };
    const { data } = await axios.post("/todos/add", todo, {
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Basic ${localStorage.getItem("user")}`,
      },
    });
    console.log(data);

    dispatch({ type: ADD_TODO, payload: data });
    e.target.reset();
  };

  return (
    <div>
      {state.isLoggedIn ? (
        <div>
          <h1 className="heading">Welcome, Jainish</h1>
          <Container maxWidth="sm">
            <form onSubmit={(e) => handleAdd(e)} className="card-content">
              <TextField
                sx={{
                  color: "#fff",
                  marginBottom: "1rem",
                  width: "100%",
                  marginRight: "1rem",
                }}
                variant="outlined"
                name="title"
              />
              <Checkbox 
                sx={{
                  color: "#fff",
                  marginBottom: "1rem",
                  width: "100%",
                  marginRight: "1rem"
                }}
                checked
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
                  key={todo.id}
                  id={todo.id}
                  content={todo.content}
                  completed={todo.completed}
                  dispatch={dispatch}
                />
              ))}
          </Container>
        </div>
      ) : (
        <div>
          <h1 className="heading">
            Welcome, User
          </h1>
          <Container>
            <h1 className="heading">
              Create Todo's List for Yourself !! Just By {" "}
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
