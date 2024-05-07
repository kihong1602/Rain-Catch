package oo.kr.shared.global.exception.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExternalAPIErrorResponse extends ErrorResponse {

  private ErrorDetail errorDetail;

  private ExternalAPIErrorResponse(HttpStatus httpStatus, ErrorDetail errorDetail) {
    super(httpStatus);
    this.errorDetail = errorDetail;
  }

  public static ExternalAPIErrorResponse create(HttpStatus httpStatus, String message) {
    return new ExternalAPIErrorResponse(httpStatus, new ErrorDetail(message));
  }
}
