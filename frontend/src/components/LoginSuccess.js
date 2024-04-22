import {useLocation, useNavigate, useSearchParams} from "react-router-dom";
import {useEffect} from "react";

function LoginSuccess() {
  const navigate = useNavigate();
  let [query] = useSearchParams();
  const {state} = useLocation();

  useEffect(() => {
    let accessToken = query.get('accessToken');
    if (accessToken) {
      sessionStorage.setItem('jwt', accessToken);
      navigate(state ? state : '/');
    }
  }, [state, navigate, query]);

  return (
      <div>Loading...</div>
  );
}

export default LoginSuccess;