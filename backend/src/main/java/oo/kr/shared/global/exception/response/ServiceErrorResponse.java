package oo.kr.shared.global.exception.response;

import java.util.Objects;
import lombok.Getter;
import oo.kr.shared.global.exception.type.RainCatchException;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceErrorResponse extends ErrorResponse {

  private ErrorDetail errorDetail;

  private ServiceErrorResponse(HttpStatus httpStatus, ErrorDetail errorDetail) {
    super(httpStatus);
    this.errorDetail = errorDetail;
  }

  public static ServiceErrorResponse create(RainCatchException ex) {
    ErrorDetail errorDetail = Objects.requireNonNullElse(ex.getErrorDetail(), new ErrorDetail(""));
    return new ServiceErrorResponse(ex.getHttpStatus(), errorDetail);
  }

  public static ServiceErrorResponse create(HttpStatus httpStatus, String message) {
    return new ServiceErrorResponse(httpStatus, new ErrorDetail(message));
  }
}
