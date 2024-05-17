package oo.kr.shared.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.jwt.CookieManager;
import oo.kr.shared.global.security.jwt.JwtProvider;
import oo.kr.shared.global.security.jwt.RefreshTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityLogoutHandler implements LogoutHandler {

  private final RefreshTokenService tokenService;
  private final JwtProvider jwtProvider;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    String accessToken = CookieManager.getAccessToken(request);
    String email = jwtProvider.getUid(accessToken);
    tokenService.removeRefreshToken(email);
  }
}
