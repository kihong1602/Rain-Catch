package oo.kr.shared.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.dto.request.RequiredPaymentInfo;
import oo.kr.shared.global.security.jwt.JwtProvider;
import oo.kr.shared.service.PaymentService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

  private final PaymentService paymentService;
  private final JwtProvider jwtProvider;

  @PostMapping("/payment/complete")
  public void completedPayment(@RequestBody RequiredPaymentInfo paymentInfo,
      @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
    String accessToken = JwtProvider.getJwt(bearerToken);
    String email = jwtProvider.getUid(accessToken);
    paymentService.completedPayment(paymentInfo, email);
  }
}