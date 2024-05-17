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
  public void saveTokenInfo(String email, String refreshToken) {
    refreshTokenRepository.save(new RefreshToken(email, refreshToken));
  }

  @Transactional
  public RefreshToken findTokenInfo(String email) {
    return refreshTokenRepository.findById(email).orElseThrow(ExpiredRefreshTokenException::new);
  }

  @Transactional
  public void removeRefreshToken(String email) {
    Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findById(email);
    if (optionalRefreshToken.isEmpty()) {
      return;
    }
    RefreshToken refreshToken = optionalRefreshToken.get();
    refreshTokenRepository.delete(refreshToken);
  }
}
