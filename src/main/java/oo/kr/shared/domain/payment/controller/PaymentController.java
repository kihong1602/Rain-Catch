package oo.kr.shared.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentInfo;
import oo.kr.shared.domain.payment.service.PaymentService;
import oo.kr.shared.global.utils.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/payment/complete")
  public void completedPayment(@RequestBody RequiredPaymentInfo paymentInfo) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    paymentService.completedPayment(paymentInfo, email);
  }
}