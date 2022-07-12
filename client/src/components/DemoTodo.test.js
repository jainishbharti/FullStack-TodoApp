import { DemoTodo } from "./DemoTodo"
import renderComponentWithContext from "../testUtils";
import {screen} from "@testing-library/react";
import TodoTimeline from "./TodoTimeline";

describe("testing demotodo component", () => {
    const contextValue = { state: {name: '', isLoggedIn: false, todos: [] }, dispatch: jest.fn() };
   test("render stepper compoenent", () => {
    renderComponentWithContext(contextValue, <DemoTodo /> );
    expect(screen.getByTestId("todostepper")).toBeInTheDocument();
    expect(screen.getByTestId("timeline")).toBeInTheDocument();

    expect(screen.getByText("What's on your mind? TODAY")).toBeInTheDocument();
    expect(screen.getByText("Timeline Example")).toBeInTheDocument();
    expect(screen.getByText("Why we need it?")).toBeInTheDocument();
    expect(screen.getByText("It makes things more handy! We don't need to do it on Paper.")).toBeInTheDocument();
   })

})