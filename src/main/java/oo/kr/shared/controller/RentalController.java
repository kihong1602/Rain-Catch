package oo.kr.shared.controller;

import javax.servlet.http.HttpSession;
import oo.kr.shared.dto.request.ReturnUmbrellaInfo;
import oo.kr.shared.dto.response.OverDueCheck;
import oo.kr.shared.global.security.SessionMember;
import oo.kr.shared.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RentalController {

  private final RentalService rentalService;

  public RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @GetMapping("/rental/umbrella/overdue")
  public ResponseEntity<OverDueCheck> returnTimeCheck(HttpSession httpSession) {
    // 우산 연체상태 조회 메서드
    SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
    OverDueCheck overDueCheck = rentalService.overdueCheck(sessionMember.getId());
    return ResponseEntity.ok(overDueCheck);
  }

  @PutMapping("/rental/umbrella/return")
  public void returnUmbrella(@RequestBody ReturnUmbrellaInfo returnUmbrellaInfo, HttpSession httpSession) {
    // 우산 반납 메서드
    SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
    rentalService.returnUmbrella(returnUmbrellaInfo, sessionMember.getId());
  }
}