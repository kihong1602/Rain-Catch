package oo.kr.shared.global.security.jwt;

public class InvalidJwtException extends RuntimeException {

  public InvalidJwtException(String message) {
    super(message);
  }
}
