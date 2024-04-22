package oo.kr.shared.domain.rentalstation.domain.repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalStationRepository extends JpaRepository<RentalStation, Long> {

  @Query(value = "select * from rental_station as r "
      + "where MBRContains(ST_LINESTRINGFROMTEXT(:point),r.point)", nativeQuery = true)
  List<RentalStation> findNearDistance(@Param("point") String point);

  @Lock(value = LockModeType.PESSIMISTIC_WRITE)
  @Query(value = "select rs from RentalStation rs where rs.id = :id")
  Optional<RentalStation> findByIdWithPessimisticLock(@Param("id") Long id);
}