package oo.kr.shared.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.exception.ErrorResponse;
import oo.kr.shared.global.security.jwt.ExpiredJwtException;
import oo.kr.shared.global.security.jwt.InvalidJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

  private final ObjectMapper objectMapper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      objectMapper.writeValue(response.getWriter(), ErrorResponse.create(HttpStatus.UNAUTHORIZED, e.getMessage()));
    } catch (InvalidJwtException e) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      objectMapper.writeValue(response.getWriter(), ErrorResponse.create(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
  }
}
