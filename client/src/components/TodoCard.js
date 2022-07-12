import React, { useState } from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import DeleteIcon from "@mui/icons-material/Delete";
import DoneIcon from "@mui/icons-material/Done";
import EditIcon from "@mui/icons-material/Edit";
import SaveIcon from "@mui/icons-material/Save";
import Input from "@mui/material/Input";
import { CardActions } from "@mui/material";
import {
  DONE_TODO,
  DELETE_TODO,
  UPDATE_TODO,
  GET_TODOS,
} from "../context/constants/ActionConstants";
import axios from "axios";

const TodoCard = ({ todoId, title, done, dispatch }) => {
  const [editing, setEditing] = useState(false);
  const [editTitle, setEditTitle] = useState(title);

  const handleChange = (e) => {
    setEditTitle(e.target.value);
  };

  const handleDone = async (e) => {
    e.preventDefault();
    const todoId = e.target.todoId.value;
    const updatedTodo = { done: !done };

    const { data, status } = await axios.put(`/todos/${todoId}`, updatedTodo, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Basic ${localStorage.getItem("user")}`,
      },
    });
    if (status === 200) {
      dispatch({ type: DONE_TODO, payload: data });
      dispatch({ type: GET_TODOS, payload: data });
    }
  };

  const handleDelete = async (e) => {
    e.preventDefault();
    const todoId = e.target.todoId.value;
    const { data } = await axios.delete(`/todos/${todoId}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Basic ${localStorage.getItem("user")}`,
      },
    });
    dispatch({ type: DELETE_TODO, payload: data });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    const todoId = e.target.todoId.value;
    const updatedTodo = { title: editTitle };
    const { data, status } = await axios.put(`/todos/${todoId}`, updatedTodo, {
      headers: {
        "Content-type": "application/json",
        Authorization: `Basic ${localStorage.getItem("user")}`,
      },
    });

    if (status === 200) {
      setEditing(false);
      dispatch({ type: UPDATE_TODO, payload: data });
      dispatch({ type: GET_TODOS, payload: data });
    }
  };

  return (
    <Card sx={{ minWidth: 275, marginBottom: "0.5rem" }}>
      <CardContent className="card-content">
        <Typography
          sx={{ fontSize: 18, marginTop: "0.7rem" }}
          style={{ textDecoration: done ? "line-through" : "none" }}
          color="text.secondary"
          component={"span"}
        >
          {editing ? (
            <Input
              type="text"
              onChange={(e) => handleChange(e)}
              value={editTitle}
              name="editTitle"
            />
          ) : (
            title
          )}
        </Typography>
        <CardActions>
          <form data-testid="complete-icon" onSubmit={(e) => handleDone(e)}>
            <input type="hidden" value={todoId} name="todoId" />
            <Button type="submit" size="small">
              {done ? null : <DoneIcon />}
            </Button>
          </form>
          <form onSubmit={(e) => handleUpdate(e)}>
            <input type="hidden" value={todoId} name="todoId" />
            <input type="hidden" value="" name="editTitle" />
            <div className="form-button">
            <div>
              <Button type="submit" size="small" disabled={!editing}>
                <SaveIcon data-testid="save-icon" type="submit" />
              </Button>
            </div>
            <div>
              <Button type="button" size="small">
                <EditIcon data-testid="update-icon"  onClick={() => setEditing(true)} />
              </Button>
            </div>
            </div>
          </form>

          <form data-testid="delete-icon" onSubmit={(e) => handleDelete(e)}>
            <input type="hidden" value={todoId} name="todoId" />
            <Button type="submit" size="small">
              <DeleteIcon />
            </Button>
          </form>
        </CardActions>
      </CardContent>
    </Card>
  );
};

export default TodoCard;
