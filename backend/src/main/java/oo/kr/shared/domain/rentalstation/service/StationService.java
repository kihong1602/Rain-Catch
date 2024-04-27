package oo.kr.shared.domain.rentalstation.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalstation.controller.request.SaveStationInfo;
import oo.kr.shared.domain.rentalstation.controller.response.NearRentalStation;
import oo.kr.shared.domain.rentalstation.controller.response.RentalStationInfo;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.rentalstation.domain.repository.RentalStationRepository;
import oo.kr.shared.global.exception.type.entity.EntityNotFoundException;
import oo.kr.shared.global.type.Location;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StationService {

  private final RentalStationRepository rentalStationRepository;

  @Transactional(readOnly = true)
  public RentalStationInfo findStationInfo(Long id) {
    RentalStation rentalStation = rentalStationRepository.findRentalStationInfo(id)
                                                         .orElseThrow(EntityNotFoundException::new);
    return RentalStationInfo.create(rentalStation);
  }

  @Transactional(readOnly = true)
  public NearRentalStation findNearStation(Location location) {
    List<RentalStation> nearRentalStation = rentalStationRepository.findNearRentalStation(location.latitude(),
        location.longitude());
    return NearRentalStation.of(nearRentalStation);
  }

  @Transactional
  public void saveStation(SaveStationInfo stationInfo) {
    RentalStation rentalStation = stationInfo.toEntity();
    rentalStationRepository.save(rentalStation);
  }

}