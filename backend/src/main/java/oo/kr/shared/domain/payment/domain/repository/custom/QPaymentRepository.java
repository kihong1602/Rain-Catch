package oo.kr.shared.domain.payment.domain.repository.custom;

import oo.kr.shared.domain.payment.domain.Payment;

public interface QPaymentRepository {

  Payment findLastPaymentByEmail(String email);
}