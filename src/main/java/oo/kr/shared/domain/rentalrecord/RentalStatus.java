package oo.kr.shared.domain.rentalrecord;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RentalStatus {

  RENTED("대여중"),
  RETURN("반납"),
  OVERDUE("연체");

  private final String description;

  RentalStatus(String description) {
    this.description = description;
  }

  @JsonValue
  public String getDescription() {
    return description;
  }
}