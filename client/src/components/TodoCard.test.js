import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import TodoCard from "./TodoCard";
import axios from "axios";

jest.mock('axios');

function renderComponent(props) {
  return render(
    <TodoCard
      todoId={props.todoId}
      title={props.title}
      done={props.done}
      dispatch={props.dispatch}
    />
  );
}

describe("Todo is displayed", () => {
  const props = {
    id: 12324,
    title: "Complete tests by EOD",
    done: false,
    dispatch: jest.fn(),
  };


  test("checking renders", () => {
    renderComponent(props);
    expect(screen.getByText("Complete tests by EOD")).toBeInTheDocument();
    expect(screen.getByTestId('update-icon')).toBeInTheDocument();
    expect(screen.getByTestId('complete-icon')).toBeInTheDocument();
    expect(screen.getByTestId('delete-icon')).toBeInTheDocument();

    fireEvent.click(screen.getByTestId('update-icon'));
    expect(screen.getByTestId('save-icon')).toBeInTheDocument();  
  });

  test("deleting todos", () => {
    renderComponent(props);
    expect(screen.getByText("Complete tests by EOD")).toBeInTheDocument();

    fireEvent.click(screen.getByTestId('delete-icon'));
    expect(screen.queryByTestId('Complete tests by EOD')).not.toBeInTheDocument();  
  });

  // test("updating todos", () => {
  //   const todo = { }
  //   axios.put.mockResolvedValueOnce({status: 200, data: {
    
  //   }})
  // })



});
