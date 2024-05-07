package oo.kr.shared.global.portone;

import oo.kr.shared.global.exception.type.payment.PaymentValidationException;

public record PaymentResult(
    Integer code,
    String message,
    Object response
) {

  private static final Integer SUCCESS_CODE = 0;

  public Object response() {
    if (isSuccess()) {
      return response;
    }
    throw new PaymentValidationException(message);
  }

  private boolean isSuccess() {
    return code.equals(SUCCESS_CODE);
  }
}
