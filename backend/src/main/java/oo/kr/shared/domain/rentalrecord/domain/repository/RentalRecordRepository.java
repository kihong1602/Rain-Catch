package oo.kr.shared.domain.rentalrecord.domain.repository;

import java.util.Optional;
import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;
import oo.kr.shared.domain.rentalrecord.domain.repository.custom.QRentalRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRecordRepository extends JpaRepository<RentalRecord, Long>, QRentalRecordRepository {

  @Query(value = "select rr from RentalRecord rr join fetch rr.umbrella where rr.payment.id=:paymentId")
  Optional<RentalRecord> findByUmbrellaIdAndPaymentId(@Param("paymentId") Long paymentId);

  @Query(value =
      "select rr from RentalRecord rr "
          + "join fetch rr.payment "
          + "join fetch rr.rentalStation "
          + "left join fetch rr.returnStation "
          + "where rr.payment.user.id = :userId",
      countQuery = "select count(rr) from RentalRecord rr join rr.payment p where p.user.id = :userId")
  Page<RentalRecord> findListByUserId(@Param("userId") Long userId, Pageable pageable);
}
