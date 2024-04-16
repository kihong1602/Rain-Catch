package oo.kr.shared.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.jwt.ExpiredJwtException;
import oo.kr.shared.global.security.jwt.InvalidJwtException;
import oo.kr.shared.global.security.jwt.JwtProvider;
import oo.kr.shared.global.security.jwt.JwtResultType;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (!StringUtils.hasText(bearerToken)) {
      doFilter(request, response, filterChain);
      return;
    }

    String token = JwtProvider.getJwt(bearerToken);

    if (StringUtils.hasText(token)) {
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
        case INVALID_JWT -> throw new InvalidJwtException("잘못된 형식의 JWT입니다.");
      }
    }
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
    try {
      String accessToken = jwtProvider.refreshAccessToken(token);
      response.setHeader(HttpHeaders.AUTHORIZATION, accessToken);
      saveAuthentication(accessToken);
    } catch (RuntimeException e) {
      throw new ExpiredJwtException("액세스 토큰이 만료되었으며, 유효한 리프레시 토큰이 없습니다.");
    }
  }
}