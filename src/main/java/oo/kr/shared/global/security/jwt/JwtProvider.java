package oo.kr.shared.global.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.security.auth.PrincipalDetails;
import oo.kr.shared.global.security.auth.SecurityUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final long REFRESH_TOKEN_EXPIRED = 1000L * 60 * 60 * 24 * 7; // Refresh Token 유효시간 7일
  private final long ACCESS_TOKEN_EXPIRED = 1000L * 60 * 30; // Access Token 유효시간 30분
  private final String AUTHORITIES_KEY = "role";
  private final RefreshTokenService tokenService;
  @Value("${jwt.secret}")
  private String secret;
  private Key key;

  public static String getJwt(String bearerToken) {
    final String TOKEN_PREFIX = "bearer ";
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
      return bearerToken.replace(TOKEN_PREFIX, "");
    }
    return null;
  }

  @PostConstruct
  protected void init() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    key = Keys.hmacShaKeyFor(keyBytes);
  }

  public GeneratedToken generateToken(Authentication authentication) {
    PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
    SecurityUserInfo securityUserInfo = user.getSecurityUserInfo();
    String userId = securityUserInfo.email();
    String authorities = authentication.getAuthorities()
                                       .stream()
                                       .map(GrantedAuthority::getAuthority)
                                       .collect(Collectors.joining(","));
    String accessToken = createToken(userId, authorities, ACCESS_TOKEN_EXPIRED);
    String refreshToken = createToken(userId, authorities, REFRESH_TOKEN_EXPIRED);
    tokenService.saveTokenInfo(securityUserInfo.email(), accessToken, refreshToken);
    return new GeneratedToken(accessToken, refreshToken);
  }

  public String refreshAccessToken(String accessToken) {
    RefreshToken tokenInfo = tokenService.findTokenInfo(accessToken);
    String refreshToken = tokenInfo.getRefreshToken();
    JwtResultType jwtResultType = verifyToken(refreshToken);
    if (jwtResultType.equals(JwtResultType.VALID_JWT)) {
      String userId = getUid(refreshToken);
      String authorities = getRole(refreshToken);
      String newAccessToken = createToken(userId, authorities, ACCESS_TOKEN_EXPIRED);
      tokenInfo.updateAccessToken(newAccessToken);
      tokenService.saveTokenInfo(tokenInfo);
      return newAccessToken;
    } else {
      throw new RuntimeException("리프레시 토큰이 만료되었습니다.");
    }
  }


  public JwtResultType verifyToken(String token) {
    try {
      getJwtParser().parseClaimsJws(token);
      return JwtResultType.VALID_JWT;
    } catch (ExpiredJwtException e) {
      return JwtResultType.EXPIRED_JWT;
    } catch (Exception e) {
      return JwtResultType.INVALID_JWT;
    }
  }

  public String getUid(String token) {
    return getJwtParser().parseClaimsJws(token)
                         .getBody()
                         .getSubject();
  }

  public String getRole(String token) {
    return getJwtParser().parseClaimsJws(token)
                         .getBody()
                         .get(AUTHORITIES_KEY, String.class);
  }

  private JwtParser getJwtParser() {
    return Jwts.parserBuilder()
               .setSigningKey(key)
               .build();
  }

  private String createToken(String userId, String authorities, long tokenExpired) {
    Date now = new Date();
    Date expiration = new Date(now.getTime() + tokenExpired);
    return Jwts.builder()
               .signWith(key, SignatureAlgorithm.HS512)
               .setSubject(userId)
               .claim(AUTHORITIES_KEY, authorities)
               .setIssuedAt(now)
               .setExpiration(expiration)
               .compact();
  }
}
