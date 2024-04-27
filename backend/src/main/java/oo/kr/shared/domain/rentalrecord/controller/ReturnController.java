package oo.kr.shared.domain.rentalrecord.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalrecord.controller.request.ReturnUmbrellaInfo;
import oo.kr.shared.domain.rentalrecord.controller.response.OverDueCheck;
import oo.kr.shared.domain.rentalrecord.service.ReturnService;
import oo.kr.shared.global.type.ResponseType;
import oo.kr.shared.global.type.SimpleResponse;
import oo.kr.shared.global.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/returns")
@RequiredArgsConstructor
public class ReturnController {

  private final ReturnService returnService;

  /**
   * 마지막 대여기록 연체여부 조회 API
   */
  @GetMapping("/umbrellas/overdue")
  public ResponseEntity<OverDueCheck> checkRentalOverDue() {
    String email = SecurityUtils.getAuthenticationPrincipal();
    OverDueCheck overDueCheck = returnService.overdueCheck(email);
    return ResponseEntity.ok(overDueCheck);
  }

  /**
   * 반납시 대여기록 수정 API
   */
  @PatchMapping("/umbrellas")
  public ResponseEntity<SimpleResponse> returnUmbrella(@RequestBody ReturnUmbrellaInfo returnUmbrellaInfo) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    returnService.returnUmbrella(returnUmbrellaInfo, email);
    return ResponseEntity.ok(new SimpleResponse(ResponseType.SUCCESS));
  }
}
