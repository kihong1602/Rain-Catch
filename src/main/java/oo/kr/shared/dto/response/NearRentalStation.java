package oo.kr.shared.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import oo.kr.shared.domain.rentalstation.RentalStation;

public class NearRentalStation {

  private List<RentalStationData> rentalStationDataList;

  private NearRentalStation(List<RentalStationData> rentalStationDataList) {
    this.rentalStationDataList = rentalStationDataList;
  }

  public static NearRentalStation of(List<RentalStation> nearStations) {
    List<RentalStationData> rentalStationData = nearStations.stream()
                                                            .map(RentalStationData::new)
                                                            .collect(Collectors.toList());
    return new NearRentalStation(rentalStationData);
  }

  public List<RentalStationData> getRentalStationDataList() {
    return rentalStationDataList;
  }

  public static class RentalStationData {

    private Long id;
    private String name;
    private String address;
    private Integer availableUmbrellas;

    private RentalStationData(RentalStation rentalStation) {
      this.id = rentalStation.getId();
      this.name = rentalStation.getName();
      this.address = rentalStation.getAddress();
      this.availableUmbrellas = rentalStation.getAvailableUmbrellas();
    }

    public Long getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getAddress() {
      return address;
    }

    public Integer getAvailableUmbrellas() {
      return availableUmbrellas;
    }
  }
}
