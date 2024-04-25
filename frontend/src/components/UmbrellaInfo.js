import customAxios from "../modules/Axios_interceptor";
import {useNavigate, useParams} from "react-router-dom";
import {useCallback, useEffect, useState} from "react";
import Button from "@mui/material/Button";

const statusList = {
  WAIT: '배치 대기 우산',
  AVAILABLE: '대여 가능',
  RENTED: '대여 불가능',
  LOST: '분실 우산',
  DAMAGED: '파손 우산'
}

function UmbrellaStatus({status}) {
  const umbrellaStatus = statusList[status] || '정보 없음';
  return <h2>대여상태 :: {umbrellaStatus}</h2>;
}

function UmbrellaStation({data}) {
  const location = data ? data.name : '대여 중';
  return <h2>우산 위치 :: {location}</h2>;
}

function UmbrellaInfo() {
  const params = useParams();
  let navigate = useNavigate();
  const [info, setInfo] = useState({
    umbrella_data: {},
    rental_station_data: {}
  });

  const getUmbrellaInfo = useCallback(() => {
    customAxios.get(`/api/rentals/umbrellas/${params.id}`)
    .then(response => setInfo(response.data))
    .catch(error => console.error(error));
  }, [params.id]);

  useEffect(() => {
    getUmbrellaInfo();
  }, [getUmbrellaInfo]);

  const onClickButton = () => {
    customAxios.get(`/api/rentals/umbrellas/current`)
    .then(response => {
      const data = response.data;
      let result = data.result;
      if (result === 'SUCCESS') {
        navigate(`/payments/umbrellas/${params.id}`);
      }
    })
    .catch(error => {
      if (error.response.status === 409) {
        const message = error.response.data.errorDetail.message;
        alert(message);
        navigate('/');
      }
      console.error(error)
    });
  }
  return (
      <div>
        <h1>우산 대여 페이지</h1>
        <UmbrellaStation data={info.rental_station_data}/>
        <UmbrellaStatus status={info.umbrella_data.umbrella_status}/>
        <Button onClick={onClickButton}> 대여하기 </Button>
      </div>
  );
}

export default UmbrellaInfo;