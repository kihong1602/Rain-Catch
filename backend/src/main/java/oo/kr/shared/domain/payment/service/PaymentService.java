package oo.kr.shared.domain.payment.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentData;
import oo.kr.shared.domain.payment.domain.Payment;
import oo.kr.shared.domain.payment.domain.repository.PaymentRepository;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.domain.user.domain.repository.UserRepository;
import oo.kr.shared.global.exception.type.payment.PaymentValidationException;
import oo.kr.shared.global.portone.PaymentClient;
import oo.kr.shared.global.portone.PreRegisterPaymentData;
import oo.kr.shared.global.portone.SinglePaymentInfo;
import oo.kr.shared.global.utils.CodeCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final UserRepository userRepository;
  private final PaymentClient paymentClient;

  public PreRegisterPaymentData preRegisterPayment(String email, Integer amount) {
    String merchantUid = CodeCreator.createMerchantUid(email);
    PreRegisterPaymentData preRegisterPaymentData = new PreRegisterPaymentData(merchantUid, amount);
    return paymentClient.preRegister(preRegisterPaymentData);
  }

  public SinglePaymentInfo validatePayment(RequiredPaymentData paymentData) {
    SinglePaymentInfo singlePaymentInfo = paymentClient.findSinglePaymentInfo(paymentData.impUid());
    boolean valid = singlePaymentInfo.isEquals(paymentData);
    if (!valid) {
      throw new PaymentValidationException();
    }
    return singlePaymentInfo;
  }

  @Transactional
  public void saveOverDuePayment(RequiredPaymentData paymentData, String email) {
    User user = userRepository.findByEmail(email)
                              .orElseThrow(EntityNotFoundException::new);
    Payment payment = Payment.create(paymentData, user);
    paymentRepository.save(payment);
  }
}