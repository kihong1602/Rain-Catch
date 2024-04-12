package oo.kr.shared.domain.umbrella;

public enum UmbrellaStatus {
  WAIT("대기중"),
  AVAILABLE("사용가능"),
  RENTED("대여 중"),
  LOST("분실"),
  DAMAGED("파손");

  private final String description;

  UmbrellaStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}