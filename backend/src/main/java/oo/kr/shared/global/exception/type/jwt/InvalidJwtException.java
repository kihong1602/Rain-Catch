package oo.kr.shared.global.exception.type.jwt;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.ServiceException;
import org.springframework.http.HttpStatus;

public class InvalidJwtException extends ServiceException {

  public InvalidJwtException() {
    super(HttpStatus.BAD_REQUEST, new ErrorDetail("잘못된 형식의 JWT입니다."));
  }
}
