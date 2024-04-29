package oo.kr.shared.domain.rentalstation.domain.repository;

import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.rentalstation.domain.repository.custom.QRentalStationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalStationRepository extends JpaRepository<RentalStation, Long>, QRentalStationRepository {

}