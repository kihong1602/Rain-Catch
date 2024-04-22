import {SERVER_URL} from "../ServerUrl";
import axios from "axios";

const customAxios = axios.create({
  baseURL: SERVER_URL // SERVER_URL = http://localhost:8080/api
});

const tokenFreeEndPoints = [
  {pattern: /^\/api\/accounts\/duplicate$/, method: 'GET'},
  {pattern: /^\/api\/accounts\/signup$/, method: 'POST'},
  {pattern: /^\/api\/accounts\/login$/, method: 'POST'},
  {pattern: /^\/api\/rentals\/umbrellas\/\d+$/, method: 'GET'},
  {pattern: /^\/api\/rental-stations\/\d+$/, method: 'GET'},
  {pattern: /^\/api\/stations\/near$/, method: 'GET'}
]

customAxios.interceptors.request.use(
    request => {
      let isRequiredToken = true;
      const requestUrl = new URL(request.url, `${SERVER_URL}`).pathname;
      const requestMethod = request.method.toUpperCase();
      tokenFreeEndPoints.forEach(({pattern, method}) => {
        if (pattern.test(requestUrl) && method === requestMethod) {
          isRequiredToken = false;
        }
      })

      if (isRequiredToken) {
        const accessToken = sessionStorage.getItem('jwt');
        if (accessToken) {
          request.headers['Authorization'] = `Bearer ${accessToken}`;
        } else {
          alert('로그인이 필요한 서비스입니다.');
          window.location.href = '/accounts/login';
        }
      }
      return request;
    },
    error => {
      console.error(error);
      return Promise.reject(error);
    }
);

customAxios.interceptors.response.use(
    response => {
      let accessToken = response.headers['authorization'];
      if (accessToken) {
        sessionStorage.setItem('jwt', accessToken);
      }
      return response;
    },
    error => {
      if (error.response.status === 401) {
        alert(error.response.data.errorDetail.message);
        window.location.href = '/accounts/login';
      }
      return Promise.reject(error);
    }
);

export default customAxios;