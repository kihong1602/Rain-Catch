import {Outlet, useLocation, useNavigate} from "react-router-dom";
import {useEffect} from "react";

const PrivateLayout = () => {
  let navigate = useNavigate();
  let {pathname} = useLocation();

  useEffect(() => {
    if (!sessionStorage.getItem('jwt')) {
      navigate('/accounts/login', {state: pathname});
    }
  }, [navigate, pathname]);

  return (
      <div>
        <Outlet/>
      </div>
  );
}

export default PrivateLayout;