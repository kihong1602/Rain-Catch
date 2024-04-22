package oo.kr.shared.domain.umbrella.domain.repository.custom;

import java.util.Optional;
import oo.kr.shared.domain.umbrella.domain.Umbrella;

public interface QUmbrellaRepository {

  Optional<Umbrella> findByUmbrellaId(Long id);
}
