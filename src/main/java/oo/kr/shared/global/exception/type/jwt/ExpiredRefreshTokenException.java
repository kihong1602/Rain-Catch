package oo.kr.shared.global.exception.type.jwt;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.ServiceException;
import org.springframework.http.HttpStatus;

public class ExpiredRefreshTokenException extends ServiceException {

  public ExpiredRefreshTokenException() {
    super(HttpStatus.UNAUTHORIZED, new ErrorDetail("Refresh Token이 만료되었습니다. 다시 로그인해주세요."));
  }
}
