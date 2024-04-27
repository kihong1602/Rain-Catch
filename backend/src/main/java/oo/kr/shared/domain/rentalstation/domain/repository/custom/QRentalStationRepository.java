package oo.kr.shared.domain.rentalstation.domain.repository.custom;

import java.util.List;
import java.util.Optional;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;

public interface QRentalStationRepository {

  Optional<RentalStation> findRentalStationInfo(Long id);

  List<RentalStation> findNearRentalStation(Double latitude, Double longitude);
}