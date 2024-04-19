import {useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import customAxios from "../modules/Axios_interceptor";
import {SERVER_URL} from "../ServerUrl";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  let navigate = useNavigate();
  const {state} = useLocation();

  const handleLogin = () => {
    const loginData = {
      email: email,
      password: password
    };
    customAxios.post(`${SERVER_URL}/accounts/login`, loginData)
    .then(() => navigate(state ? state : '/'))
    .catch(error => console.error(error));
  }

  const oAuth2LoginServerUrl = SERVER_URL.replace('/api', '');

  return (
      <div>
        <h1>Login Page</h1>
        <TextField type="text" placeholder="email" name="email" value={email} onChange={e => setEmail(e.target.value)}/>
        <TextField type="password" placeholder="password" name="password" value={password}
                   onChange={e => setPassword(e.target.value)}/>
        <Button onClick={handleLogin}>Login</Button>
        <Button>
          <a href={`${oAuth2LoginServerUrl}/oauth2/authorization/google`}>Google Login</a>
        </Button>
        <Button>
          <a href={`${oAuth2LoginServerUrl}/oauth2/authorization/kakao`}>Kakao Login</a>
        </Button>
      </div>
  )
}

export default Login;