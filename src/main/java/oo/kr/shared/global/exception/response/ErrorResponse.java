package oo.kr.shared.global.exception.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ErrorResponse {

  @JsonIgnore
  private final HttpStatus httpStatus;

  private final String code;

  protected ErrorResponse(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
    code = httpStatus.name();
  }
}
