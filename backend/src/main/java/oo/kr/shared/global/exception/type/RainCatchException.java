package oo.kr.shared.global.exception.type;

import lombok.Getter;
import oo.kr.shared.global.exception.response.ErrorDetail;
import org.springframework.http.HttpStatus;

@Getter
public abstract class RainCatchException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final ErrorDetail errorDetail;

  protected RainCatchException(HttpStatus httpStatus, ErrorDetail errorDetail) {
    super(errorDetail.message());
    this.httpStatus = httpStatus;
    this.errorDetail = errorDetail;
  }

  protected RainCatchException(HttpStatus httpStatus, ErrorDetail errorDetail, Throwable ex) {
    super(errorDetail.message(), ex);
    this.httpStatus = httpStatus;
    this.errorDetail = errorDetail;
  }

}
