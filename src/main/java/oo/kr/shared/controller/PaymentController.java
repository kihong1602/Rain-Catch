package oo.kr.shared.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.dto.request.RequiredPaymentInfo;
import oo.kr.shared.global.security.SessionMember;
import oo.kr.shared.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/payment/complete")
  public void completedPayment(@RequestBody RequiredPaymentInfo paymentInfo, HttpSession httpSession) {
    SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
    paymentService.completedPayment(paymentInfo, sessionMember.id());
  }
}