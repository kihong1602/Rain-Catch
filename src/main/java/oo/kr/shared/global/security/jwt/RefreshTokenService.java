package oo.kr.shared.global.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;

  @Transactional
  public void saveTokenInfo(String email, String refreshToken, String accessToken) {
    refreshTokenRepository.save(new RefreshToken(email, accessToken, refreshToken));
  }

  @Transactional
  public void saveTokenInfo(RefreshToken refreshToken) {
    refreshTokenRepository.save(refreshToken);
  }

  @Transactional
  public RefreshToken findTokenInfo(String accessToken) {
    return refreshTokenRepository.findByAccessToken(accessToken)
                                 .orElseThrow(() -> new RuntimeException("리프레시 토큰이 존재하지 않습니다."));
  }

  @Transactional
  public void removeRefreshToken(String accessToken) {
    RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(accessToken)
                                                      .orElseThrow(RuntimeException::new);
    refreshTokenRepository.delete(refreshToken);
  }
}
