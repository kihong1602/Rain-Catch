package oo.kr.shared.domain.rentalrecord.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalrecord.controller.request.ReturnUmbrellaInfo;
import oo.kr.shared.domain.rentalrecord.controller.response.OverDueCheck;
import oo.kr.shared.domain.rentalrecord.controller.response.UmbrellaInfo;
import oo.kr.shared.domain.rentalrecord.service.RentalService;
import oo.kr.shared.global.type.ResponseType;
import oo.kr.shared.global.type.SimpleResponse;
import oo.kr.shared.global.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

  private final RentalService rentalService;

  @GetMapping("/umbrellas/{id}")
  public ResponseEntity<UmbrellaInfo> findUmbrellaInfo(@PathVariable("id") Long id) {
    UmbrellaInfo umbrellaInfo = rentalService.findUmbrellaInfo(id);
    return ResponseEntity.ok(umbrellaInfo);
  }

  @GetMapping("/umbrellas/current")
  public ResponseEntity<SimpleResponse> findRecentRentalRecord() {
    String email = SecurityUtils.getAuthenticationPrincipal();
    boolean result = rentalService.findCurrentRentalByUser(email);
    SimpleResponse response = new SimpleResponse(ResponseType.of(result));
    return ResponseEntity.ok(response);
  }

  @GetMapping("/rental/umbrella/overdue")
  public ResponseEntity<OverDueCheck> returnTimeCheck() {
    String email = SecurityUtils.getAuthenticationPrincipal();
    OverDueCheck overDueCheck = rentalService.overdueCheck(email);
    return ResponseEntity.ok(overDueCheck);
  }

  @PutMapping("/rental/umbrella/return")
  public ResponseEntity<SimpleResponse> returnUmbrella(@RequestBody ReturnUmbrellaInfo returnUmbrellaInfo) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    rentalService.returnUmbrella(returnUmbrellaInfo, email);
    return ResponseEntity.ok(new SimpleResponse(ResponseType.SUCCESS));
  }
}