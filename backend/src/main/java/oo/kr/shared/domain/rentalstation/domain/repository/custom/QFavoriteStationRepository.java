package oo.kr.shared.domain.rentalstation.domain.repository.custom;

import java.util.List;
import oo.kr.shared.domain.rentalstation.domain.FavoriteStation;

public interface QFavoriteStationRepository {

  List<FavoriteStation> findFavoritesByEmail(String email);

  Long findByEmailAndStationId(String email, Long id);
}