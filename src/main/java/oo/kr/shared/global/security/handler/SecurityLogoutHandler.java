package oo.kr.shared.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.jwt.JwtProvider;
import oo.kr.shared.global.security.jwt.RefreshTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityLogoutHandler implements LogoutHandler {

  private final RefreshTokenService tokenService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    String accessToken = JwtProvider.getJwt(bearerToken);
    tokenService.removeRefreshToken(accessToken);
  }
}
