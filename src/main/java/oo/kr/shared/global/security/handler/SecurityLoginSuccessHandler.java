package oo.kr.shared.global.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.user.ProviderType;
import oo.kr.shared.global.security.auth.PrincipalDetails;
import oo.kr.shared.global.security.auth.SecurityUserInfo;
import oo.kr.shared.global.security.jwt.GeneratedToken;
import oo.kr.shared.global.security.jwt.JwtProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class SecurityLoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtProvider jwtProvider;
  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    GeneratedToken generatedToken = jwtProvider.generateToken(authentication);
    PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
    SecurityUserInfo userInfo = user.getSecurityUserInfo();
    ProviderType providerType = userInfo.providerType();
    if (providerType.equals(ProviderType.LOCAL)) {
      jsonLoginSuccessProcess(response, generatedToken);
    } else {
      oAuth2LoginSuccessProcess(request, response, generatedToken);
    }
  }

  private void jsonLoginSuccessProcess(HttpServletResponse response, GeneratedToken generatedToken) {
    response.setHeader(HttpHeaders.AUTHORIZATION, generatedToken.accessToken());
    response.setStatus(HttpStatus.OK.value());
  }

  private void oAuth2LoginSuccessProcess(HttpServletRequest request, HttpServletResponse response,
      GeneratedToken generatedToken)
      throws IOException {
    String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/login-success")
                                           .queryParam("accessToken", generatedToken.accessToken())
                                           .build()
                                           .encode(StandardCharsets.UTF_8)
                                           .toUriString();
    redirectStrategy.sendRedirect(request, response, targetUrl);
  }
}