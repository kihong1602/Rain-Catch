package oo.kr.shared.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalstation.RentalStation;
import oo.kr.shared.domain.rentalstation.RentalStationRepository;
import oo.kr.shared.dto.request.SaveStationInfo;
import oo.kr.shared.dto.response.NearRentalStation;
import oo.kr.shared.global.utils.Direction;
import oo.kr.shared.global.utils.GeometryUtil;
import oo.kr.shared.global.utils.Location;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StationService {

  private final RentalStationRepository rentalStationRepository;

  public NearRentalStation findNearStation(Location location) {
    Double distance = 0.5;
    Location northEast = GeometryUtil
        .calculate(location.latitude(), location.longitude(), distance, Direction.NORTHEAST.getBearing());
    Location southWest = GeometryUtil
        .calculate(location.latitude(), location.longitude(), distance, Direction.SOUTHWEST.getBearing());

    double x1 = northEast.latitude();
    double y1 = northEast.longitude();
    double x2 = southWest.latitude();
    double y2 = southWest.longitude();

    String point = String.format("LINESTRING(%f %f, %f %f)", x1, y1, x2, y2);
    List<RentalStation> nearStations = rentalStationRepository.findNearDistance(point);
    return NearRentalStation.of(nearStations);
  }

  public void saveStation(SaveStationInfo stationInfo) {
    RentalStation rentalStation = stationInfo.toEntity();
    rentalStationRepository.save(rentalStation);
  }

}