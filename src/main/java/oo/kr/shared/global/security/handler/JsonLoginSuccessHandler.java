package oo.kr.shared.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.jwt.GeneratedToken;
import oo.kr.shared.global.security.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtProvider jwtProvider;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    GeneratedToken generatedToken = jwtProvider.generateToken(authentication);
    response.setHeader(HttpHeaders.AUTHORIZATION, generatedToken.accessToken());
    response.setStatus(HttpStatus.OK.value());
  }
}
