package oo.kr.shared.global.exception.type.payment;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.PaymentException;
import org.springframework.http.HttpStatus;

public class OtherHttpStatus4xxException extends PaymentException {

  public OtherHttpStatus4xxException(String message) {
    super(HttpStatus.BAD_REQUEST, new ErrorDetail(message));
  }
}
