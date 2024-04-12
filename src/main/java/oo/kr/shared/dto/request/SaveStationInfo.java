package oo.kr.shared.dto.request;

import oo.kr.shared.domain.rentalstation.RentalStation;

public class SaveStationInfo {

  private String name;
  private String address;
  private Double latitude;
  private Double longitude;

  public RentalStation toEntity() {
    return new RentalStation(name, address, null, latitude, longitude);
  }
}
