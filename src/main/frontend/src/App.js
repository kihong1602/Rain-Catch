import './App.css';
import {BrowserRouter, Route, Routes,} from "react-router-dom";
import Main from "./components/Main";
import Login from "./components/Login";
import LoginSuccess from "./components/LoginSuccess";
import Signup from "./components/Signup";
import PrivateLayout from "./components/PrivateLayout";
import payment from "./components/Payment";
import UmbrellaInfo from "./components/UmbrellaInfo";

function App() {

  return (
      <BrowserRouter>
        <Routes>
          <Route path="/" Component={Main}></Route>
          <Route path="/accounts/signup" Component={Signup}></Route>
          <Route path="/accounts/login" Component={Login}></Route>
          <Route path="/login-success" Component={LoginSuccess}></Route>
          <Route path="/rentals/umbrellas/:id" Component={UmbrellaInfo}></Route>
          <Route element={<PrivateLayout/>}>
            <Route path="/payments/umbrellas/:id" Component={payment}></Route>
          </Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
