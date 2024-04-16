package oo.kr.shared.domain.user.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.user.controller.request.SignupInfo;
import oo.kr.shared.domain.user.controller.response.DuplicateEmailResult;
import oo.kr.shared.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class SignUpController {

  private final UserService userService;

  @GetMapping("/duplicate")
  public ResponseEntity<DuplicateEmailResult> duplicateEmail(@RequestParam("email") String email) {
    DuplicateEmailResult duplicateEmailResult = userService.duplicateCheck(email);
    return ResponseEntity.ok(duplicateEmailResult);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> register(@RequestBody SignupInfo signupInfo) {
    userService.register(signupInfo);
    return ResponseEntity.ok("SUCCESS");
  }
}
