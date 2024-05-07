package oo.kr.shared.global.exception.type;

import oo.kr.shared.global.exception.response.ErrorDetail;
import org.springframework.http.HttpStatus;

public abstract class ServiceException extends RainCatchException {

  protected ServiceException(HttpStatus httpStatus, ErrorDetail errorDetail) {
    super(httpStatus, errorDetail);
  }

  protected ServiceException(HttpStatus httpStatus, ErrorDetail errorDetail, Throwable ex) {
    super(httpStatus, errorDetail, ex);
  }
}