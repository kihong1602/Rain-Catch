package oo.kr.shared.service;

import java.util.List;
import oo.kr.shared.domain.rentalstation.RentalStation;
import oo.kr.shared.domain.rentalstation.RentalStationRepository;
import oo.kr.shared.dto.request.SaveStationInfo;
import oo.kr.shared.dto.response.NearRentalStation;
import oo.kr.shared.global.utils.Direction;
import oo.kr.shared.global.utils.GeometryUtil;
import oo.kr.shared.global.utils.Location;
import org.springframework.stereotype.Service;

@Service
public class StationService {

  private final RentalStationRepository rentalStationRepository;

  public StationService(RentalStationRepository rentalStationRepository) {
    this.rentalStationRepository = rentalStationRepository;
  }

  public NearRentalStation findNearStation(Location location) {
    Double distance = 0.5;
    Location northEast = GeometryUtil
        .calculate(location.getLatitude(), location.getLongitude(), distance, Direction.NORTHEAST.getBearing());
    Location southWest = GeometryUtil
        .calculate(location.getLatitude(), location.getLongitude(), distance, Direction.SOUTHWEST.getBearing());

    double x1 = northEast.getLatitude();
    double y1 = northEast.getLongitude();
    double x2 = southWest.getLatitude();
    double y2 = southWest.getLongitude();

    String point = String.format("LINESTRING(%f %f, %f %f)", x1, y1, x2, y2);
    List<RentalStation> nearStations = rentalStationRepository.findNearDistance(point);
    return NearRentalStation.of(nearStations);
  }

  public void saveStation(SaveStationInfo stationInfo) {
    RentalStation rentalStation = stationInfo.toEntity();
    rentalStationRepository.save(rentalStation);
  }

}