package oo.kr.shared.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.controller.request.RequiredAmount;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentData;
import oo.kr.shared.domain.payment.controller.response.PreRegisterPaymentInfo;
import oo.kr.shared.domain.payment.service.PaymentService;
import oo.kr.shared.global.portone.PreRegisterPaymentData;
import oo.kr.shared.global.portone.SinglePaymentInfo;
import oo.kr.shared.global.type.ResponseType;
import oo.kr.shared.global.type.SimpleResponse;
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

  /**
   * 결제금액 사전등록 API
   */
  @PostMapping("/pre-registration")
  public ResponseEntity<PreRegisterPaymentInfo> preRegisterPayment(@RequestBody RequiredAmount requiredAmount) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    PreRegisterPaymentData preRegisterResult = paymentService.preRegisterPayment(email, requiredAmount.amount());
    return ResponseEntity.ok(new PreRegisterPaymentInfo(email, preRegisterResult));
  }

  /**
   * 결제내역 사후검증 API
   */
  @PostMapping("/validation")
  public ResponseEntity<SinglePaymentInfo> validatePayment(@RequestBody RequiredPaymentData paymentData) {
    SinglePaymentInfo singlePaymentInfo = paymentService.validatePayment(paymentData);
    return ResponseEntity.ok(singlePaymentInfo);
  }

  /**
   * 결제내역 저장 API <br/> 우산대여가 아닌, 연체금액 결제시 사용
   */
  @PostMapping
  public ResponseEntity<SimpleResponse> saveOverDuePayment(@RequestBody RequiredPaymentData paymentData) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    paymentService.saveOverDuePayment(paymentData, email);
    return ResponseEntity.ok(new SimpleResponse(ResponseType.SUCCESS));
  }

}