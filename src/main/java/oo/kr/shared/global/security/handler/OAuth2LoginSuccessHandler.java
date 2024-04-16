package oo.kr.shared.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.jwt.GeneratedToken;
import oo.kr.shared.global.security.jwt.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtProvider jwtProvider;
  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    GeneratedToken generatedToken = jwtProvider.generateToken(authentication);
    String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/login-success")
                                           .queryParam("accessToken", generatedToken.accessToken())
                                           .build()
                                           .encode(StandardCharsets.UTF_8)
                                           .toUriString();
    redirectStrategy.sendRedirect(request, response, targetUrl);
  }
}
