import Navbar from "./Navbar";
import renderComponentWithContext from "../testUtils";
import {act, screen, fireEvent, render, waitFor} from "@testing-library/react";
import userEvent from "@testing-library/user-event";

function getWindowSize() {
    const {innerWidth, innerHeight} = window;
    return {innerWidth, innerHeight};
  }

  afterEach(() => {
    global.innerWidth = 1920
    global.dispatchEvent(new Event('resize'))
  });

test("Navbar shows username and logout when logged in", () => {
    const contextValue = { state: {name: 'Test User', isLoggedIn: true, todos: [] }, dispatch: jest.fn() };
    renderComponentWithContext(contextValue, <Navbar />);
    expect(screen.getByText(/Hello, Test/i)).toBeInTheDocument();
    expect(screen.getByRole("logoutButton")).toBeInTheDocument();
    userEvent.click(screen.getByRole("logoutButton"));
    // screen.debug();
});

test("Navbar shows login and signup when not logged in", () => {
    const contextValue = { state: {name: '', isLoggedIn: false, todos: [] }, dispatch: jest.fn() };
    renderComponentWithContext(contextValue, <Navbar />);
    expect(screen.getByText(/Login/i)).toBeInTheDocument();
    expect(screen.getByText(/SignUp/i)).toBeInTheDocument();
});

// test("When width is below 500px ", () => {
//     const contextValue = { state: {name: '', isLoggedIn: false, todos: [] } };
//     act(() => {
//         window.innerWidth = 450;
//         // fireEvent(window, new Event("resize"));
//         // console.log(window.innerWidth);
        
//         expect(screen.getByTestId("hamburger")).toBeInTheDocument();
//     })
   
//     renderComponentWithContext(contextValue, <Navbar />);
//     expect(screen.getByText(/Login/i)).toBeInTheDocument();
//     expect(screen.getByText(/SignUp/i)).toBeInTheDocument();
// });



// test('resize to mobile', async () => {
//     const { getByText } = render();
//     // Change the viewport to 500px.
//     global.innerWidth = 450;
//   // Trigger the window resize event.
//     global.dispatchEvent(new Event('resize'));
//   // wait react component render after event dispatch
//     expect(screen.getByText(/login/i)).not.toBeInTheDocument();
//     expect(screen.getByText(/signup/i)).not.toBeInTheDocument();
//     expect(screen.getByRole('presentation')).toBeInTheDocument();
//   });