package oo.kr.shared.global.exception.handler;

import oo.kr.shared.global.exception.response.ErrorResponse;
import oo.kr.shared.global.exception.response.ServiceErrorResponse;
import oo.kr.shared.global.exception.response.ValidationErrorResponse;
import oo.kr.shared.global.exception.type.ProjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ProjectException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorResponse> handleServiceAndValidateException(Exception ex) {
    ErrorResponse response = createErrorResponse(ex);
    return ResponseEntity.status(response.getHttpStatus())
                         .body(response);
  }

  private ErrorResponse createErrorResponse(Exception ex) {
    if (ex instanceof MethodArgumentNotValidException validException) {
      return ValidationErrorResponse.create(HttpStatus.BAD_REQUEST, validException.getBindingResult());
    } else {
      return ServiceErrorResponse.create((ProjectException) ex);
    }
  }
}
