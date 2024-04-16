package oo.kr.shared.domain.payment.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentStatus {

  COMPLETED("결제 완료"),
  CANCELLED("결제 취소");

  private final String description;

  @JsonValue
  public String getDescription() {
    return description;
  }
}
