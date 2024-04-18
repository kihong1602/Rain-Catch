import {useNavigate} from "react-router-dom";
import Button from "@mui/material/Button";

function Main() {

  const navigate = useNavigate();

  const handleLogin = () => {
    navigate('/accounts/login');
  }

  const handleSignup = () => {
    navigate('/accounts/signup')
  }

  const handleUmbrellaInfo = () => {
    navigate('/rentals/umbrellas/1')
  }

  return (
      <div>
        <h1>Main Page</h1>
        <Button onClick={handleLogin}>Login</Button>
        <Button onClick={handleSignup}>Signup</Button>
        <Button onClick={handleUmbrellaInfo}>rentalUmbrella</Button>
      </div>
  );
}

export default Main;