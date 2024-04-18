package oo.kr.shared.domain.payment.domain.repository.custom;

import static oo.kr.shared.domain.payment.domain.QPayment.payment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.domain.Payment;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QPaymentRepositoryImpl implements QPaymentRepository {

  private final JPAQueryFactory query;

  @Override
  public Payment findLastPaymentByEmail(String email) {
    return query.select(payment)
                .from(payment)
                .where(payment.user.email.eq(email))
                .orderBy(payment.createDate.desc())
                .fetchFirst();
  }
}
