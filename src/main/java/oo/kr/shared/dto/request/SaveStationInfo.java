package oo.kr.shared.dto.request;

import oo.kr.shared.domain.rentalstation.RentalStation;

public record SaveStationInfo(
    String name,
    String address,
    Double latitude,
    Double longitude
) {

  public RentalStation toEntity() {
    return new RentalStation(name, address, null, latitude, longitude);
  }
}
