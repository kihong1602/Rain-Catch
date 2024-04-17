package oo.kr.shared.global.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

  public static String getAuthenticationPrincipal() {
    Authentication authentication = SecurityContextHolder.getContext()
                                                         .getAuthentication();
    return (String) authentication.getPrincipal();
  }

}