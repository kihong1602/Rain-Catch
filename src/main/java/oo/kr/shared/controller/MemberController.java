package oo.kr.shared.controller;

import javax.servlet.http.HttpSession;
import oo.kr.shared.dto.response.RentalRecordData;
import oo.kr.shared.global.security.SessionMember;
import oo.kr.shared.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/member/rental/record")
  public ResponseEntity<Page<RentalRecordData>> viewRentalRecord(
      @PageableDefault(sort = "createDate", direction = Sort.Direction.ASC) Pageable pageable,
      HttpSession httpSession) {
    SessionMember sessionMember = (SessionMember) httpSession.getAttribute("user");
    Page<RentalRecordData> rentalRecordData = memberService.viewRentalRecord(sessionMember.getId(), pageable);
    return ResponseEntity.ok(rentalRecordData);
  }
}
