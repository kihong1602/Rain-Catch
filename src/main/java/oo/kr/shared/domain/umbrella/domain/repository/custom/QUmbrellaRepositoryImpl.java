package oo.kr.shared.domain.umbrella.domain.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.umbrella.domain.QUmbrella;
import oo.kr.shared.domain.umbrella.domain.Umbrella;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QUmbrellaRepositoryImpl implements QUmbrellaRepository {

  private final JPAQueryFactory query;

  @Override
  public Optional<Umbrella> findByUmbrellaId(Long id) {
    Umbrella umbrella = query.selectFrom(QUmbrella.umbrella)
                             .leftJoin(QUmbrella.umbrella.currentStation)
                             .fetchJoin()
                             .where(QUmbrella.umbrella.id.eq(id))
                             .fetchOne();
    return Optional.ofNullable(umbrella);
  }
}
