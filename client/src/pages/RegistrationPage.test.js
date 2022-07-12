import { screen } from "@testing-library/react";
import renderComponentWithContext from "../testUtils";
import RegistrationPage from "./LoginPage";


describe("testing registration page", () => {
    const contextValue = { state: {name: '', isLoggedIn: false, todos: [] }, dispatch: jest.fn() };

    test("render registation component", () => {
        renderComponentWithContext(contextValue, <RegistrationPage />);
    });
  });