package oo.kr.shared.global.portone;

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
    // 실패한 이유가 message에 담김. 해당 예외 추가 설정
    throw new RuntimeException(message);
  }

  private boolean isSuccess() {
    return code.equals(SUCCESS_CODE);
  }
}
