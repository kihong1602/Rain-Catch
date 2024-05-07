package oo.kr.shared.global.exception.type.payment;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.PaymentException;
import org.springframework.http.HttpStatus;

public class PaymentValidationException extends PaymentException {

  public PaymentValidationException(String message) {
    super(HttpStatus.CONFLICT, new ErrorDetail(message));
  }

  public PaymentValidationException() {
    super(HttpStatus.CONFLICT, new ErrorDetail("입력된 결제내용과 검증결과가 다릅니다."));
  }
}