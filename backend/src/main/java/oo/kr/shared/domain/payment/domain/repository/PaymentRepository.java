package oo.kr.shared.domain.payment.domain.repository;

import java.util.List;
import oo.kr.shared.domain.payment.domain.Payment;
import oo.kr.shared.domain.payment.domain.repository.custom.QPaymentRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, QPaymentRepository {

  @Query(value = "select p from Payment p where p.user.email = :email order by p.createDate desc")
  List<Payment> findByUserId(@Param("email") String email, Pageable pageable);
}