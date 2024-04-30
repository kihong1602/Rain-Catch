package oo.kr.shared.global.exception.type.payment;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.PaymentException;
import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends PaymentException {

  public InvalidAccessTokenException(String message) {
    super(HttpStatus.UNAUTHORIZED, new ErrorDetail(message));
  }
}
