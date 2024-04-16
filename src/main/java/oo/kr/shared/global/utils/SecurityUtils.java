package oo.kr.shared.global.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import oo.kr.shared.global.security.auth.PrincipalDetails;
import oo.kr.shared.global.security.auth.SecurityUserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

  public static SecurityUserInfo getSecurityUserInfo() {
    Authentication authentication = SecurityContextHolder.getContext()
                                                         .getAuthentication();
    PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
    return principal.getSecurityUserInfo();
  }

}