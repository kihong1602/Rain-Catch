package oo.kr.shared.domain.rentalstation.domain.repository.custom;

import static oo.kr.shared.domain.rentalstation.domain.QFavoriteStation.favoriteStation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalstation.domain.FavoriteStation;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QFavoriteStationRepositoryImpl implements QFavoriteStationRepository {

  private final JPAQueryFactory query;

  @Override
  public List<FavoriteStation> findFavoritesByEmail(String email) {
    return query.selectFrom(favoriteStation)
                .join(favoriteStation.rentalStation)
                .fetchJoin()
                .join(favoriteStation.user)
                .fetchJoin()
                .where(favoriteStation.user.email.eq(email))
                .fetch();
  }

  @Override
  public Long findByEmailAndStationId(String email, Long id) {
    return query.delete(favoriteStation)
                .where(favoriteStation.user.email.eq(email)
                                                 .and(favoriteStation.rentalStation.id.eq(id)))
                .execute();
  }
}
