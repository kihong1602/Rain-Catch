import Button from "@mui/material/Button";
import {useCallback, useEffect, useState} from "react";
import {usePortOne} from "../modules/usePortOne";
import customAxios from "../modules/Axios_interceptor";
import {JWTParser} from "../modules/JWTParser";
import {useNavigate, useParams} from "react-router-dom";

function Payment({overdueAmount}) {
  usePortOne();
  let navigate = useNavigate();
  const params = useParams();
  const [merchantUid, setMerchantUid] = useState('');
  const [amount, setAmount] = useState(overdueAmount || 1000);

  const isEmpty = useCallback((value) => !value || value.trim() === '', []);

  const preRegisterPaymentInfo = useCallback(() => {
    const data = {
      amount: amount
    };
    customAxios.post(`/api/payments/pre-registration`, data)
    .then(response => response.data)
    .then(data => {
      setMerchantUid(data.merchant_uid);
      setAmount(data.amount);
      sessionStorage.setItem('paymentInfo', JSON.stringify({merchantUid: data.merchant_uid, amount: data.amount}));
    }).catch(error => console.error(error));
  }, [amount]);

  useEffect(() => {
    const paymentInfo = sessionStorage.getItem('paymentInfo');
    if (paymentInfo) {
      const {merchantUid, amount} = JSON.parse(paymentInfo);
      setMerchantUid(merchantUid);
      setAmount(amount);
    } else {
      preRegisterPaymentInfo();
    }
  }, [overdueAmount, preRegisterPaymentInfo, isEmpty]);

  const onClickPay = async () => {
    const email = JWTParser();
    const {IMP} = window;
    IMP.init(process.env.REACT_APP_IMP_CODE);
    console.log(`amount :: ${amount}`);
    console.log(`merchantUid :: ${merchantUid}`);
    const data = {
      pg: 'tosspayments',
      pay_method: 'toss',
      name: '우산 1일 대여',
      amount: amount, // 내려온 데이터 입력
      merchant_uid: merchantUid,
      buyer_tel: email
    }

    IMP.request_pay(data, async res => {
      console.log(res);
      if (!res.error_code) {
        const impUid = res.imp_uid;
        const merchantUid = res.merchant_uid;

        const data = {
          imp_uid: impUid,
          merchant_uid: merchantUid,
          amount: amount
        };
        let validatePaymentInfo = await validatePayment(data);
        savePayment(validatePaymentInfo);
        sessionStorage.removeItem('paymentInfo');
      } else {
        alert(res.error_msg);
      }
    });

    const validatePayment = (data) => {
      return customAxios.post(`/api/payments/validation`, data)
      .then(response => response.data)
      .catch(error => {
        console.error(error);
        return Promise.reject(error);
      });
    }

    const savePayment = (data) => {
      customAxios.post(`/api/rentals/umbrellas/${params.id}`, data)
      .then(() => {
        navigate('/payments/success')
      })
      .catch(error => {
        console.error(error);
        return Promise.reject(error);
      });
    }
  }

  return (
      <div>
        <Button onClick={onClickPay}>결제하기</Button>
      </div>
  )
}

export default Payment;