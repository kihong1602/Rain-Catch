package oo.kr.shared.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.controller.request.RequiredAmount;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentInfo;
import oo.kr.shared.domain.payment.service.PaymentService;
import oo.kr.shared.global.portone.PreRegisterPaymentData;
import oo.kr.shared.global.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/pre-registration")
  public ResponseEntity<PreRegisterPaymentData> preRegisterPayment(@RequestBody RequiredAmount requiredAmount) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    PreRegisterPaymentData preRegisterResult = paymentService.preRegisterPayment(email, requiredAmount.amount());
    return ResponseEntity.ok(preRegisterResult);
  }

  @PostMapping("/payment/complete")
  public void completedPayment(@RequestBody RequiredPaymentInfo paymentInfo) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    paymentService.completedPayment(paymentInfo, email);
  }
}