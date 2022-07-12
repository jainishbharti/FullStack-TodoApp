import { screen } from "@testing-library/react";
import renderComponentWithContext from "../testUtils";
import HomePage from "./HomePage";

import axios from "axios";

jest.mock('axios');

test("When the user is logged in", () => {
  const contextValue = {
    state: { name: "Test", isLoggedIn: true, todos: [] },
    dispatch: jest.fn(),
  };
  renderComponentWithContext(contextValue, <HomePage />);

  expect(screen.getByText("Todo List")).toBeInTheDocument();
  expect(screen.getByText("Helps you keep precise and organized !")).toBeInTheDocument();
  expect(screen.getByText("Todo List")).toBeInTheDocument();
  expect(screen.getByTestId("checkbox")).toBeInTheDocument();
  expect(screen.getByRole("addtodobutton")).toBeInTheDocument();
  expect(screen.getByPlaceholderText("What's on your mind today?")).toBeInTheDocument();
});

test("When the user is not logged in", () => {
  const contextValue = {
    state: { name: "", isLoggedIn: false, todos: [] },
    dispatch: jest.fn(),
  };
  renderComponentWithContext(contextValue, <HomePage />);

  expect(screen.getByText("What's on your mind? TODAY")).toBeInTheDocument();
  expect(screen.getByTestId("demotodo")).toBeInTheDocument();
});

test("When user adds a todo", async () => {
  const contextValue = {
    state: { name: "", isLoggedIn: false, todos: [] },
    dispatch: jest.fn(),
  };
  renderComponentWithContext(contextValue, <HomePage />);
  const data = {
      todoId: 1,
      title: 'Get tests done by EOD',
      done: false,
      user: { userId: 1, name: 'John Doe', email: 'johnny@gmail.com', password: 'something'}
  }
  axios.post.mockResolvedValueOnce(data);

  //when
  const response = await axios.post('http://localhost:8080/todo/api/todos', {
    title: 'Get tests done by EOD',
    done: false,
    user: { userId: 1, name: 'John Doe', email: 'johnny@gmail.com', password: 'something'}
  });

  //then
  expect(response).toEqual(data);
})

test("When user fetch a todos", async () => {
  const contextValue = {
    state: { name: "", isLoggedIn: false, todos: [] },
    dispatch: jest.fn(),
  };
  renderComponentWithContext(contextValue, <HomePage />);
  const data = {
      todoId: 1,
      title: 'Get tests done by EOD',
      done: false,
      user: { userId: 1, name: 'John Doe', email: 'johnny@gmail.com', password: 'something'}
  }
  axios.get.mockResolvedValueOnce(data);

  //when
  const response = await axios.get('http://localhost:8080/todo/api/todos/user/1');
  //then
  expect(response).toEqual(data);
})

test("When user checking login", async () => {
  const contextValue = {
    state: { name: "", isLoggedIn: false, todos: [] },
    dispatch: jest.fn(),
  };
  renderComponentWithContext(contextValue, <HomePage />);
  const data = {
       userId: 1, name: 'John Doe', email: 'johnny@gmail.com', password: 'something'
  }
  axios.get.mockResolvedValueOnce({status: 202, data});

  //when
  const response = await axios.get('http://localhost:8080/todo/api/auth/checkLogin');
  //then
  expect(response.data).toEqual(data);
})
