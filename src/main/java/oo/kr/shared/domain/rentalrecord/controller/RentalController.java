package oo.kr.shared.domain.rentalrecord.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentData;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rentals")
@RequiredArgsConstructor
public class RentalController {

  private final RentalService rentalService;

  /**
   * 대여할 우산 정보 조회 API
   */
  @GetMapping("/umbrellas/{id}")
  public ResponseEntity<UmbrellaInfo> findUmbrellaInfo(@PathVariable("id") Long id) {
    UmbrellaInfo umbrellaInfo = rentalService.findUmbrellaInfo(id);
    return ResponseEntity.ok(umbrellaInfo);
  }

  /**
   * 미 반납 대여기록 유무 조회 API
   */
  @GetMapping("/umbrellas/current")
  public ResponseEntity<SimpleResponse> findRecentRentalRecord() {
    String email = SecurityUtils.getAuthenticationPrincipal();
    boolean result = rentalService.findCurrentRentalByUser(email);
    SimpleResponse response = new SimpleResponse(ResponseType.of(result));
    return ResponseEntity.ok(response);
  }

  /**
   * 대여기록 등록 API
   */
  @PostMapping("/umbrellas/{id}")
  public ResponseEntity<String> saveRentalRecord(@PathVariable("id") Long umbrellaId,
      @RequestBody RequiredPaymentData paymentData) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    rentalService.saveRentalRecordAndPayment(email, umbrellaId, paymentData);
    return ResponseEntity.ok("SUCCESS");
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