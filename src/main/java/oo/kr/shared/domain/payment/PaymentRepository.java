package oo.kr.shared.domain.payment;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

  @Query(value = "select p from Payment p where p.member.id = :memberId order by p.createDate desc")
  List<Payment> findByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}