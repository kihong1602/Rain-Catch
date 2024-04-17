package oo.kr.shared.domain.rentalrecord.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.umbrella.domain.Umbrella;
import oo.kr.shared.domain.umbrella.domain.UmbrellaStatus;
import org.locationtech.jts.geom.Point;

public record UmbrellaInfo(
    @JsonProperty("umbrella_data")
    UmbrellaData umbrellaData,

    @JsonProperty("rental_station_data")
    RentalStationData rentalStationData
) {

  public static UmbrellaInfo create(Umbrella umbrella) {
    UmbrellaData umbrellaData = new UmbrellaData(umbrella.getId(), umbrella.getUmbrellaStatus());
    RentalStationData rentalStationData = RentalStationData.create(umbrella.getCurrentStation());
    return new UmbrellaInfo(umbrellaData, rentalStationData);
  }

  private record UmbrellaData(
      Long id,

      @JsonProperty("umbrella_status")
      UmbrellaStatus umbrellaStatus
  ) {

  }

  private record RentalStationData(
      String name,

      Double longitude,

      Double latitude
  ) {

    private static RentalStationData create(RentalStation rentalStation) {
      if (Objects.nonNull(rentalStation)) {
        Point point = rentalStation.getPoint();
        return new RentalStationData(rentalStation.getName(), point.getX(), point.getY());
      } else {
        return null;
      }
    }
  }
}