import {useEffect} from "react";

export const usePortOne = () => {
  useEffect(() => {
    let portOne = document.createElement('script');
    portOne.src = 'https://cdn.iamport.kr/v1/iamport.js';
    document.head.appendChild(portOne);
    return () => {
      document.head.removeChild(portOne);
    }
  }, []);
}