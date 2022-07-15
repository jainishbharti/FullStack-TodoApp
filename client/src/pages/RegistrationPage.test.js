import renderComponentWithContext from "../testUtils";
import RegistrationPage from "./RegistrationPage";


describe("testing registration page", () => {
    const contextValue = { state: {name: '', isLoggedIn: false, todos: [] }, dispatch: jest.fn() };

    test("render registation component", () => {
        renderComponentWithContext(contextValue, <RegistrationPage />);

    });
  });