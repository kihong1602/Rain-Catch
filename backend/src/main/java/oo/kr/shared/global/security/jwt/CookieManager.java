package oo.kr.shared.global.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieManager {

  private static final String COOKIE_NAME = "AccessToken";

  public static void createJWTCookie(HttpServletResponse response, String accessToken) {
    ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, accessToken)
                                          .httpOnly(true)
                                          .maxAge(Duration.ofDays(14L))
                                          .sameSite("Strict")
                                          .path("/")
                                          .build();
    response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public static String getAccessToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getCookies())
                   .flatMap(CookieManager::getCookieValue)
                   .orElse(null);
  }

  private static Optional<String> getCookieValue(Cookie[] cookies) {
    return Arrays.stream(cookies)
                 .filter(cookie -> cookie.getName().equals(COOKIE_NAME))
                 .map(Cookie::getValue)
                 .findFirst();
  }
}