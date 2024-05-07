package oo.kr.shared.global.exception.type.payment;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.PaymentException;
import org.springframework.http.HttpStatus;

public class APIResponseNotFoundException extends PaymentException {

  public APIResponseNotFoundException(Throwable ex) {
    super(HttpStatus.SERVICE_UNAVAILABLE, new ErrorDetail("API서버에서 응답이 도착하지 않았습니다."), ex);
  }
}