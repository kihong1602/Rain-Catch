package oo.kr.shared.global.security.jwt;

public class ExpiredJwtException extends RuntimeException {

  public ExpiredJwtException(String message) {
    super(message);
  }
}
