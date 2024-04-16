package oo.kr.shared.global.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.filter.JsonAuthenticationFilter;
import oo.kr.shared.global.security.filter.JwtAuthenticationFilter;
import oo.kr.shared.global.security.filter.JwtExceptionFilter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@RequiredArgsConstructor
public class SecurityFilterConfig {

  private final JsonAuthenticationFilter jsonAuthenticationFilter;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtExceptionFilter jwtExceptionFilter;

}