package oo.kr.shared.global.security;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import oo.kr.shared.global.exception.type.jwt.ExpiredRefreshTokenException;
import oo.kr.shared.global.security.jwt.RefreshTokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class RefreshTokenServiceTest {

  @Autowired
  private RefreshTokenService refreshTokenService;

  @Test
  @DisplayName("리프레시 토큰이 만료되었다면 ExpiredRefreshTokenException이 발생한다.")
  void expiredTokenExceptionTest() {
    assertThatThrownBy(() -> refreshTokenService.findTokenInfo("expiredToken"))
        .isInstanceOf(ExpiredRefreshTokenException.class);
  }
}