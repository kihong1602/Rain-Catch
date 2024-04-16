package oo.kr.shared.global.security.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import oo.kr.shared.global.security.handler.SecurityLoginSuccessHandler;
import oo.kr.shared.global.utils.JsonUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  private static final String JSON_FORM_LOGIN_URL = "/api/accounts/login";
  private static final AntPathRequestMatcher JSON_FORM_LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher(
      JSON_FORM_LOGIN_URL, HttpMethod.POST.name());

  private final JsonUtils jsonUtils;

  public JsonAuthenticationFilter(JsonUtils jsonUtils, AuthenticationManager authenticationManager,
      SecurityLoginSuccessHandler securityLoginSuccessHandler) {
    super(JSON_FORM_LOGIN_REQUEST_MATCHER, authenticationManager);
    setAuthenticationSuccessHandler(securityLoginSuccessHandler);
    this.jsonUtils = jsonUtils;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    final String REQUEST_CONTENT_TYPE = request.getContentType();
    validateContentType(REQUEST_CONTENT_TYPE);

    LoginData loginData = jsonUtils.deserializeJsonToObject(request.getInputStream(), LoginData.class);
    validateLoginData(loginData);

    Authentication authRequest = new UsernamePasswordAuthenticationToken(loginData.email, loginData.password);

    return super.getAuthenticationManager()
                .authenticate(authRequest);
  }

  private void validateContentType(String REQUEST_CONTENT_TYPE) {
    if (Objects.isNull(REQUEST_CONTENT_TYPE) || !REQUEST_CONTENT_TYPE.equals(MediaType.APPLICATION_JSON_VALUE)) {
      throw new AuthenticationServiceException("Authentication Content-Type not supported: " + REQUEST_CONTENT_TYPE);
    }
  }

  private void validateLoginData(LoginData loginData) {
    if (!StringUtils.hasText(loginData.email) || !StringUtils.hasText(loginData.password)) {
      throw new AuthenticationServiceException("Error: Data is Miss");
    }
  }

  private record LoginData(
      String email,
      String password
  ) {

  }
}