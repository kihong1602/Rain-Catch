import axios from "axios";

const customAxios = axios.create();

customAxios.interceptors.response.use(
    response => {
      return response;
    }, error => {
      if (error.response.status === 401) {
        alert(error.response.data.errorDetail.message);
        window.location.href = '/accounts/login';
      }
      return Promise.reject(error);
    }
);

export default customAxios;