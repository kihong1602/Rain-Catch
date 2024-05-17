import {useLocation, useNavigate, useSearchParams} from "react-router-dom";
import {useEffect} from "react";

function LoginSuccess() {
  const navigate = useNavigate();
  let [query] = useSearchParams();
  const {state} = useLocation();

  useEffect(() => {
    navigate(state ? state : '/');
  }, [state, navigate, query]);

  return (
      <div>Loading...</div>
  );
}

export default LoginSuccess;