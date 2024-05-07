package oo.kr.shared.global.exception.type.payment;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.PaymentException;
import org.springframework.http.HttpStatus;

public class InvalidImpUidException extends PaymentException {

  public InvalidImpUidException(String message) {
    super(HttpStatus.NOT_FOUND, new ErrorDetail(message));
  }
}
