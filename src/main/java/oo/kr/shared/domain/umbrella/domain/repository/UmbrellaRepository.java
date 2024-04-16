package oo.kr.shared.domain.umbrella.domain.repository;

import oo.kr.shared.domain.umbrella.domain.Umbrella;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UmbrellaRepository extends JpaRepository<Umbrella, Long> {

}
