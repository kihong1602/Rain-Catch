package oo.kr.shared.global.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.user.domain.ProviderType;
import oo.kr.shared.global.security.auth.PrincipalDetails;
import oo.kr.shared.global.security.auth.SecurityUserInfo;
import oo.kr.shared.global.security.jwt.CookieManager;
import oo.kr.shared.global.security.jwt.GeneratedToken;
import oo.kr.shared.global.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityLoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtProvider jwtProvider;
  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
  @Value("${login.redirect-url}")
  private String redirectUrl;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    GeneratedToken generatedToken = jwtProvider.generateToken(authentication);
    PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
    SecurityUserInfo userInfo = user.getSecurityUserInfo();
    ProviderType providerType = userInfo.providerType();
    CookieManager.createJWTCookie(response, generatedToken.accessToken());
    if (providerType.equals(ProviderType.LOCAL)) {
      jsonLoginSuccessProcess(response);
    } else {
      oAuth2LoginSuccessProcess(request, response);
    }
  }

  private void jsonLoginSuccessProcess(HttpServletResponse response) {
    response.setStatus(HttpStatus.OK.value());
  }

  private void oAuth2LoginSuccessProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
    redirectStrategy.sendRedirect(request, response, redirectUrl);
  }
}