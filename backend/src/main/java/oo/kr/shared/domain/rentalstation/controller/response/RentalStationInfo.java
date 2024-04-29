package oo.kr.shared.domain.rentalstation.controller.response;

import oo.kr.shared.domain.rentalstation.domain.RentalStation;

public record RentalStationInfo(
    String name,
    String address,
    Double latitude,
    Double longitude
) {

  public static RentalStationInfo create(RentalStation rentalStation) {
    return new RentalStationInfo(rentalStation.getName(), rentalStation.getAddress(), rentalStation.getLatitude(),
        rentalStation.getLongitude());
  }

}