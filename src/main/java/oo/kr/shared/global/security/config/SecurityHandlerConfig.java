package oo.kr.shared.global.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.handler.OAuth2LoginSuccessHandler;
import oo.kr.shared.global.security.handler.SecurityAccessDeniedHandler;
import oo.kr.shared.global.security.handler.SecurityAuthenticationEntryPoint;
import oo.kr.shared.global.security.handler.SecurityLogoutHandler;
import oo.kr.shared.global.security.handler.SecurityLogoutSuccessHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@RequiredArgsConstructor
public class SecurityHandlerConfig {

  private final OAuth2LoginSuccessHandler OAuth2LoginSuccessHandler;
  private final SecurityLogoutHandler logoutHandler;
  private final SecurityLogoutSuccessHandler logoutSuccessHandler;
  private final SecurityAccessDeniedHandler accessDeniedHandler;
  private final SecurityAuthenticationEntryPoint entryPoint;
}
