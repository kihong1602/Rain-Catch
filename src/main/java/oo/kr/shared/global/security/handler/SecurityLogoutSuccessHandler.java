package oo.kr.shared.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.jwt.JwtProvider;
import oo.kr.shared.global.security.jwt.RefreshTokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityLogoutSuccessHandler implements LogoutSuccessHandler {

  private final RefreshTokenService tokenService;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    String accessToken = JwtProvider.getJwt(bearerToken);
    tokenService.removeRefreshToken(accessToken);
    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter()
            .write("Logout Success");
  }
}
