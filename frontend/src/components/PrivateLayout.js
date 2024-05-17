import {Outlet, useLocation, useNavigate} from "react-router-dom";
import {useEffect} from "react";
import customAxios from "../modules/Axios_interceptor";

const PrivateLayout = () => {
  let navigate = useNavigate();
  let {pathname} = useLocation();

  useEffect(() => {
    customAxios.get('/api/users/me').then(response => response.data)
    .then(data => {
      if (data.result !== 'SUCCESS') {
        navigate('/accounts/login', {state: pathname});
      }
    })
    .catch(error => {
      const data = error.response.data;
      alert(data.errorDetail.message);
      navigate('/accounts/login', {state: pathname});
    });
  }, [navigate, pathname]);

  return (
      <div>
        <Outlet/>
      </div>
  );
}

export default PrivateLayout;