package oo.kr.shared.global.security.config;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.auth.CustomUserDetailsService;
import oo.kr.shared.global.security.filter.JsonAuthenticationFilter;
import oo.kr.shared.global.security.handler.JsonLoginSuccessHandler;
import oo.kr.shared.global.utils.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityBeanConfig {

  private final CustomUserDetailsService userDetailsService;
  private final JsonLoginSuccessHandler jsonLoginSuccessHandler;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(bCryptPasswordEncoder());
    provider.setUserDetailsService(userDetailsService);
    return new ProviderManager(provider);
  }

  @Bean
  public JsonAuthenticationFilter jsonUsernamePasswordAuthenticationFilter(JsonUtils jsonUtils,
      AuthenticationManager authenticationManager) {
    JsonAuthenticationFilter filter = new JsonAuthenticationFilter(jsonUtils,
        authenticationManager);
    filter.setAuthenticationSuccessHandler(jsonLoginSuccessHandler);
    return filter;
  }
}
