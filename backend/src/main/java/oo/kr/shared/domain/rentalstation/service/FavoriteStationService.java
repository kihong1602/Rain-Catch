package oo.kr.shared.domain.rentalstation.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalstation.controller.response.RentalStationInfo;
import oo.kr.shared.domain.rentalstation.domain.FavoriteStation;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.rentalstation.domain.repository.FavoriteStationRepository;
import oo.kr.shared.domain.rentalstation.domain.repository.RentalStationRepository;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.domain.user.domain.repository.UserRepository;
import oo.kr.shared.global.type.ResponseType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteStationService {

  private static final Long ZERO = 0L;
  private final FavoriteStationRepository favoriteStationRepository;
  private final RentalStationRepository rentalStationRepository;
  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public List<RentalStationInfo> findFavoriteStations(String email) {
    List<FavoriteStation> favorites = favoriteStationRepository.findFavoritesByEmail(email);
    return favorites.stream()
                    .map(FavoriteStation::getRentalStation)
                    .map(RentalStationInfo::create)
                    .toList();
  }

  @Transactional
  public void saveFavoriteStation(String email, Long stationId) {
    User user = userRepository.findByEmail(email)
                              .orElseThrow(EntityNotFoundException::new);
    RentalStation rentalStation = rentalStationRepository.findById(stationId)
                                                         .orElseThrow(EntityNotFoundException::new);
    FavoriteStation favoriteStation = new FavoriteStation(user, rentalStation);
    favoriteStationRepository.save(favoriteStation);
  }

  @Transactional
  public ResponseType removeFavoriteStation(String email, Long stationId) {
    Long result = favoriteStationRepository.findByEmailAndStationId(email, stationId);
    return result.equals(ZERO) ? ResponseType.SUCCESS : ResponseType.FAIL;
  }
}
