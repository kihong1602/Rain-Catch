package oo.kr.shared.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.dto.request.ReturnUmbrellaInfo;
import oo.kr.shared.dto.response.OverDueCheck;
import oo.kr.shared.global.security.SessionMember;
import oo.kr.shared.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RentalController {

  private final RentalService rentalService;

  @GetMapping("/rental/umbrella/overdue")
  public ResponseEntity<OverDueCheck> returnTimeCheck(HttpSession httpSession) {
    SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
    OverDueCheck overDueCheck = rentalService.overdueCheck(sessionMember.id());
    return ResponseEntity.ok(overDueCheck);
  }

  @PutMapping("/rental/umbrella/return")
  public void returnUmbrella(@RequestBody ReturnUmbrellaInfo returnUmbrellaInfo, HttpSession httpSession) {
    SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
    rentalService.returnUmbrella(returnUmbrellaInfo, sessionMember.id());
  }
}