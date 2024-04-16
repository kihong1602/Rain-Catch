package oo.kr.shared.domain.rentalstation.controller.response;

import java.util.List;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;

public record NearRentalStation(
    List<RentalStationData> rentalStationDataList
) {

  public static NearRentalStation of(List<RentalStation> nearStations) {
    List<RentalStationData> rentalStationData = nearStations.stream()
                                                            .map(RentalStationData::create)
                                                            .toList();
    return new NearRentalStation(rentalStationData);
  }


  public record RentalStationData(
      Long id,
      String name,
      String address,
      Integer availableUmbrellas
  ) {

    private static RentalStationData create(RentalStation rentalStation) {
      return new RentalStationData(rentalStation.getId(), rentalStation.getName(), rentalStation.getAddress(),
          rentalStation.getAvailableUmbrellas()
                       .size());
    }

  }
}