package oo.kr.shared.global.exception.response;

import lombok.Getter;
import net.minidev.json.annotate.JsonIgnore;
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
