package oo.kr.shared.domain.rentalrecord.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalrecord.controller.request.ReturnUmbrellaInfo;
import oo.kr.shared.domain.rentalrecord.controller.response.OverDueCheck;
import oo.kr.shared.domain.rentalrecord.service.RentalService;
import oo.kr.shared.global.security.auth.SecurityUserInfo;
import oo.kr.shared.global.security.jwt.JwtProvider;
import oo.kr.shared.global.type.ResponseType;
import oo.kr.shared.global.type.SimpleResponse;
import oo.kr.shared.global.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RentalController {

  private final RentalService rentalService;
  private final JwtProvider jwtProvider;

  @GetMapping("/rental/umbrella/overdue")
  public ResponseEntity<OverDueCheck> returnTimeCheck() {
    SecurityUserInfo securityUserInfo = SecurityUtils.getSecurityUserInfo();
    OverDueCheck overDueCheck = rentalService.overdueCheck(securityUserInfo);
    return ResponseEntity.ok(overDueCheck);
  }

  @PutMapping("/rental/umbrella/return")
  public ResponseEntity<SimpleResponse> returnUmbrella(@RequestBody ReturnUmbrellaInfo returnUmbrellaInfo) {
    SecurityUserInfo securityUserInfo = SecurityUtils.getSecurityUserInfo();
    rentalService.returnUmbrella(returnUmbrellaInfo, securityUserInfo);
    return ResponseEntity.ok(new SimpleResponse(ResponseType.SUCCESS));
  }
}