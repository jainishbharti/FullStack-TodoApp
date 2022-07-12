import { act, fireEvent, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";

import renderComponentWithContext from "../testUtils";
import Login from "./Login";
import axios from "axios";

jest.mock("axios");

const contextValue = {
  state: { name: "", isLoggedIn: false, todos: [] },
  dispatch: jest.fn(),
};

describe("login", () => {
  test("login form should be displayed", () => {
    renderComponentWithContext(contextValue, <Login />);
    expect(screen.getByLabelText("Email")).toBeInTheDocument();
    expect(screen.getByLabelText("Password")).toBeInTheDocument();
    expect(screen.getByRole("loginButton")).toBeInTheDocument();
  });

  describe("login function", () => {
    const email = "johnny@email.com";
    const password = "hexagon";

    describe("with success", () => {
      const data = {
        // status: 200,
        name: "John",
        // token : "Basic am9obm55QGdtYWlsLmNvbTpoZXhhZ29u"
      };

      it("should return response data", async () => {
        axios.post.mockImplementationOnce(() => {
          // localStorage.setItem("test-user", data.token);
          return Promise.resolve(data);
        });
        const response = await axios.post("http://localhost:8080/api/login", {
          email,
          password,
        });
        expect(response).toStrictEqual(data);
        // expect(localStorage.getItem("test-user")).toBe(response.token);
      });
    });

    describe("with failure", () => {
      it("should return error", async () => {
        const errorMessage = "Invalid Credentials";
        axios.post.mockImplementationOnce(() =>
          Promise.reject(new Error(errorMessage))
        );
        renderComponentWithContext(contextValue, <Login />);

        const response = axios.post("http://localhost:8080/api/login", {
          email,
          password,
        });
        await expect(response).rejects.toThrow(errorMessage);
        fireEvent.change(screen.getByLabelText("Email"), {
          target: { value: email },
        });
        fireEvent.change(screen.getByLabelText("Password"), {
          target: { value: password },
        });
        await act(async () => {
          userEvent.click(screen.getByRole("loginButton"));
        });
        expect(screen.getByText("Invalid Credentials")).toBeInTheDocument();
      });
    });
  });
});
