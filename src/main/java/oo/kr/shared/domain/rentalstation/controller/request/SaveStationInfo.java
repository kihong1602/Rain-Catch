package oo.kr.shared.domain.rentalstation.controller.request;

import oo.kr.shared.domain.rentalstation.domain.RentalStation;

public record SaveStationInfo(
    String name,
    String address,
    Double latitude,
    Double longitude
) {

  public RentalStation toEntity() {
    return new RentalStation(name, address, latitude, longitude);
  }
}
