package oo.kr.shared.domain.payment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentStatus {

  COMPLETED("결제 완료"),
  CANCELLED("결제 취소");

  private final String description;

  PaymentStatus(String description) {
    this.description = description;
  }

  @JsonValue
  public String getDescription() {
    return description;
  }
}
