package oo.kr.shared.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.exception.type.jwt.InvalidJwtException;
import oo.kr.shared.global.security.jwt.CookieManager;
import oo.kr.shared.global.security.jwt.JwtProvider;
import oo.kr.shared.global.security.jwt.JwtResultType;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;
  private final PathMatcher pathMatcher = new AntPathMatcher();
  private final List<EndPoint> tokenFreeEndPoints = List.of(
      new EndPoint("/api/accounts/duplicate", HttpMethod.GET),
      new EndPoint("/api/accounts/signup", HttpMethod.POST),
      new EndPoint("/api/accounts/login", HttpMethod.POST),
      new EndPoint("/api/rentals/umbrellas/*", HttpMethod.GET),
      new EndPoint("/api/stations/*", HttpMethod.GET)
  );

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String requestURI = request.getRequestURI();
    String method = request.getMethod().toUpperCase();
    if (isTokenFreeEndPoint(requestURI, method)) {
      doFilter(request, response, filterChain);
      return;
    }
    String token = CookieManager.getAccessToken(request);

    JwtResultType jwtResultType = jwtProvider.verifyToken(token);
    switch (jwtResultType) {
      case VALID_JWT -> {
        saveAuthentication(token);
        filterChain.doFilter(request, response);
      }
      case EXPIRED_JWT -> {
        refreshAccessToken(token, response);
        filterChain.doFilter(request, response);
      }
      case INVALID_JWT -> throw new InvalidJwtException();
    }
  }

  private boolean isTokenFreeEndPoint(String requestURI, String requestMethod) {
    return tokenFreeEndPoints.stream()
                             .anyMatch(endpoint ->
                                 pathMatcher.match(endpoint.pattern, requestURI)
                                     && endpoint.method.matches(requestMethod));

  }

  private void saveAuthentication(String token) {
    String email = jwtProvider.getUid(token);
    String role = jwtProvider.getRole(token);
    List<SimpleGrantedAuthority> authorities = Arrays.stream(role.split(","))
                                                     .map(SimpleGrantedAuthority::new)
                                                     .toList();
    Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
    SecurityContextHolder.getContext()
                         .setAuthentication(authentication);
  }

  private void refreshAccessToken(String token, HttpServletResponse response) {
    String accessToken = jwtProvider.refreshAccessToken(token);
    CookieManager.createJWTCookie(response, accessToken);
    saveAuthentication(accessToken);
  }

  private record EndPoint(
      String pattern,
      HttpMethod method
  ) {

  }
}