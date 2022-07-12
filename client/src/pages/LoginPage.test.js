import { screen } from "@testing-library/react";
import renderComponentWithContext from "../testUtils";
import LoginPage from "./LoginPage";


describe("testing login page", () => {
        const contextValue = { state: {name: '', isLoggedIn: false, todos: [] }, dispatch: jest.fn() };

    test("render login component", () => {
      renderComponentWithContext(contextValue, <LoginPage />);
      expect(screen.getByTestId("logincomp")).toBeInTheDocument();
      
    });
  });
  