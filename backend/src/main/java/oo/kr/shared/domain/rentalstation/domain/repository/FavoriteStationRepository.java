package oo.kr.shared.domain.rentalstation.domain.repository;

import oo.kr.shared.domain.rentalstation.domain.FavoriteStation;
import oo.kr.shared.domain.rentalstation.domain.repository.custom.QFavoriteStationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteStationRepository extends JpaRepository<FavoriteStation, Long>, QFavoriteStationRepository {

}