package oo.kr.shared.global.security.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.auth.CustomOAuth2UserService;
import oo.kr.shared.global.security.filter.JsonAuthenticationFilter;
import oo.kr.shared.global.security.filter.JwtAuthenticationFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final CustomOAuth2UserService oAuth2UserService;
  private final SecurityFilterConfig filterConfig;
  private final SecurityHandlerConfig handlerConfig;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()));

    http.authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
        .requestMatchers("/api/accounts/**")
        .permitAll()
        .requestMatchers(HttpMethod.GET, "/api/rentals/umbrellas/**")
        .permitAll()
        .anyRequest()
        .authenticated());

    http.sessionManagement(managementConfigurer -> managementConfigurer
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.exceptionHandling(handlingConfigurer -> handlingConfigurer
        .authenticationEntryPoint(handlerConfig.getEntryPoint())
        .accessDeniedHandler(handlerConfig.getAccessDeniedHandler()));

    http.oauth2Login(oAuth2LoginConfigurer -> oAuth2LoginConfigurer
        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
            .userService(oAuth2UserService))
        .successHandler(handlerConfig.getLoginSuccessHandler()));

    http.logout(logoutConfigurer -> logoutConfigurer
        .logoutUrl("/api/accounts/logout")
        .addLogoutHandler(handlerConfig.getLogoutHandler())
        .logoutSuccessHandler(handlerConfig.getLogoutSuccessHandler()));

    http.addFilterBefore(filterConfig.getJsonAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(filterConfig.getJwtAuthenticationFilter(), JsonAuthenticationFilter.class)
        .addFilterBefore(filterConfig.getJwtExceptionFilter(), JwtAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web -> web
        .ignoring()
        .requestMatchers(
            PathRequest.toStaticResources()
                       .atCommonLocations()
        );
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    List<String> origins = List.of("*");
    List<String> httpMethods = List.of(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
        HttpMethod.DELETE.name());
    List<String> headers = List.of("X-Requested-With", "Content-Type", "Authorization", "X-XSRF-token");

    configuration.setAllowedOrigins(origins);
    configuration.setAllowedMethods(httpMethods);
    configuration.setAllowedHeaders(headers);
    configuration.setAllowCredentials(false);
    configuration.setMaxAge(3600L);
    // Authorization header Read 권한 추가
    configuration.addExposedHeader(HttpHeaders.AUTHORIZATION);
    configuration.applyPermitDefaultValues();

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}