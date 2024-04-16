package oo.kr.shared.domain.rentalrecord.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalrecord.controller.request.ReturnUmbrellaInfo;
import oo.kr.shared.domain.rentalrecord.controller.response.OverDueCheck;
import oo.kr.shared.domain.rentalrecord.service.RentalService;
import oo.kr.shared.global.security.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RentalController {

  private final RentalService rentalService;
  private final JwtProvider jwtProvider;

  @GetMapping("/rental/umbrella/overdue")
  public ResponseEntity<OverDueCheck> returnTimeCheck(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
    String accessToken = JwtProvider.getJwt(bearerToken);
    String email = jwtProvider.getUid(accessToken);
    OverDueCheck overDueCheck = rentalService.overdueCheck(email);
    return ResponseEntity.ok(overDueCheck);
  }

  @PutMapping("/rental/umbrella/return")
  public void returnUmbrella(@RequestBody ReturnUmbrellaInfo returnUmbrellaInfo,
      @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
    String accessToken = JwtProvider.getJwt(bearerToken);
    String email = jwtProvider.getUid(accessToken);
    rentalService.returnUmbrella(returnUmbrellaInfo, email);
  }
}