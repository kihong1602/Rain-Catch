package oo.kr.shared.domain.payment.service;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentData;
import oo.kr.shared.global.portone.PaymentClient;
import oo.kr.shared.global.portone.PreRegisterPaymentData;
import oo.kr.shared.global.portone.SinglePaymentInfo;
import oo.kr.shared.global.utils.CodeCreator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {

  private final PaymentClient paymentClient;

  public PreRegisterPaymentData preRegisterPayment(String email, Integer amount) {
    String merchantUid = CodeCreator.createMerchantUid(email);
    PreRegisterPaymentData preRegisterPaymentData = new PreRegisterPaymentData(merchantUid, amount);
    return paymentClient.preRegister(preRegisterPaymentData);
  }

  public SinglePaymentInfo validatePayment(RequiredPaymentData paymentData) {
    // 단건조회 api 실행
    SinglePaymentInfo singlePaymentInfo = paymentClient.findSinglePaymentInfo(paymentData.impUid());
    boolean valid = singlePaymentInfo.isEquals(paymentData);
    if (!valid) {
      // 검증에 실패하였다는 예외 반환 예정
      throw new RuntimeException();
    }
    return singlePaymentInfo;
  }

}