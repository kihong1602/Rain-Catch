package oo.kr.shared.global.security.jwt;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.exception.type.jwt.ExpiredRefreshTokenException;
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
                                 .orElseThrow(ExpiredRefreshTokenException::new);
  }

  @Transactional
  public void removeRefreshToken(String accessToken) {
    Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByAccessToken(accessToken);
    if (optionalRefreshToken.isEmpty()) {
      return;
    }
    RefreshToken refreshToken = optionalRefreshToken.get();
    refreshTokenRepository.delete(refreshToken);
  }
}
