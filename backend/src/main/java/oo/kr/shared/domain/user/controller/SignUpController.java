package oo.kr.shared.domain.user.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.user.controller.request.SignupInfo;
import oo.kr.shared.domain.user.controller.response.DuplicateEmailResult;
import oo.kr.shared.domain.user.service.UserService;
import oo.kr.shared.global.type.ResponseType;
import oo.kr.shared.global.type.SimpleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class SignUpController {

  private final UserService userService;

  /**
   * 이메일 중복 체크 API
   */
  @GetMapping("/duplicate")
  public ResponseEntity<DuplicateEmailResult> duplicateEmail(@RequestParam("email") String email) {
    DuplicateEmailResult duplicateEmailResult = userService.duplicateCheck(email);
    return ResponseEntity.ok(duplicateEmailResult);
  }

  /**
   * 회원가입 API
   */
  @PostMapping("/signup")
  public ResponseEntity<SimpleResponse> register(@RequestBody SignupInfo signupInfo) {
    userService.register(signupInfo);
    return ResponseEntity.ok(new SimpleResponse(ResponseType.SUCCESS));
  }
}