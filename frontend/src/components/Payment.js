import Button from "@mui/material/Button";
import {useCallback, useEffect, useState} from "react";
import {usePortOne} from "../modules/usePortOne";
import customAxios from "../modules/Axios_interceptor";
import {useNavigate, useParams} from "react-router-dom";

function Payment({overdueAmount}) {
  usePortOne();
  let navigate = useNavigate();
  const params = useParams();
  const [paymentInfo, setPaymentInfo] = useState({
    email: '',
    merchantUid: '',
    amount: overdueAmount || 1000
  });

  const isEmpty = useCallback((value) => !value || value.trim() === '', []);

  const preRegisterPaymentInfo = useCallback(() => {
    const data = {
      amount: paymentInfo.amount
    };
    customAxios.post(`/api/payments/pre-registration`, data)
    .then(response => response.data)
    .then(data => {
      const paymentData = data.payment_data;
      const updatePaymentInfo = {
        email: data.email,
        merchantUid: paymentData.merchant_uid,
        amount: paymentData.amount
      };
      setPaymentInfo(updatePaymentInfo);
      sessionStorage.setItem('paymentInfo', JSON.stringify(updatePaymentInfo));
    }).catch(error => console.error(error));
  }, [paymentInfo.amount]);

  useEffect(() => {
    const storedPaymentInfo = sessionStorage.getItem('paymentInfo');
    if (storedPaymentInfo) {
      setPaymentInfo(JSON.parse(storedPaymentInfo));
    } else {
      preRegisterPaymentInfo();
    }
  }, [overdueAmount, preRegisterPaymentInfo, isEmpty]);

  const onClickPay = async () => {
    const {IMP} = window;
    IMP.init(process.env.REACT_APP_IMP_CODE);
    console.log(`amount :: ${paymentInfo.amount}`);
    console.log(`merchantUid :: ${paymentInfo.merchantUid}`);
    const data = {
      pg: 'tosspayments',
      pay_method: 'toss',
      name: '우산 1일 대여',
      amount: paymentInfo.amount, // 내려온 데이터 입력
      merchant_uid: paymentInfo.merchantUid,
      buyer_tel: paymentInfo.email
    }

    IMP.request_pay(data, async res => {
      console.log(res);
      if (!res.error_code) {
        const impUid = res.imp_uid;
        const merchantUid = res.merchant_uid;

        const data = {
          imp_uid: impUid,
          merchant_uid: merchantUid,
          amount: paymentInfo.amount
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