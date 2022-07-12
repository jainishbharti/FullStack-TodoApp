import { screen } from "@testing-library/react";
import renderComponentWithContext from "../testUtils";
import TodoStepper from "./Stepper";

describe("testing stepper component", () => {
  const contextValue = {
    name: "",
    isLoggedIn: false,
    todos: [],
    dispatch: jest.fn(),
  };
  test("render stepper component", () => {
    renderComponentWithContext(contextValue, <TodoStepper />);
    expect(
      screen.getByText("Create an Account By Signing Up")
    ).toBeInTheDocument();
    expect(screen.getByText("Make a Todo List for Today")).toBeInTheDocument();
    expect(
      screen.getByText("Complete them & Mark as completed")
    ).toBeInTheDocument();
  });
});
