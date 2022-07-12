import { screen } from "@testing-library/react";
import renderComponentWithContext from "../testUtils";
import TodoTimeline  from "./TodoTimeline";

describe("testing timeline component", () => {
  const contextValue = {
    name: "",
    isLoggedIn: false,
    todos: [],
    dispatch: jest.fn(),
  };

  test("render timeline component", () => {
    renderComponentWithContext(contextValue, <TodoTimeline />);
    expect(screen.getByText('9:30 am')).toBeInTheDocument();
    expect(screen.getByText('CREATE TASK')).toBeInTheDocument();
    expect(screen.getByText('Decided to get something done!')).toBeInTheDocument();
    expect(screen.getByText('10:00 am')).toBeInTheDocument();
    expect(screen.getByText('MODIFY TASK')).toBeInTheDocument();
    expect(screen.getByText('Because you want to integrate some more tasks')).toBeInTheDocument();
    expect(screen.getByText('MARK COMPLETED')).toBeInTheDocument();
    expect(screen.getByText('Because you have completed it!')).toBeInTheDocument();
    expect(screen.getByText('DELETE')).toBeInTheDocument();
    expect(screen.getByText('Because this task has been pretty old!')).toBeInTheDocument();
  });
});
