package oo.kr.shared.global.exception.type.payment;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.PaymentException;
import org.springframework.http.HttpStatus;

public class APIServerErrorException extends PaymentException {

  public APIServerErrorException() {
    super(HttpStatus.INTERNAL_SERVER_ERROR, new ErrorDetail("결제 API 서버에 장애가 발생하였습니다."));
  }
}