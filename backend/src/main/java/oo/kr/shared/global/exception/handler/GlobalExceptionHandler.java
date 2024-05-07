package oo.kr.shared.global.exception.handler;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import oo.kr.shared.global.exception.response.ErrorResponse;
import oo.kr.shared.global.exception.response.ExternalAPIErrorResponse;
import oo.kr.shared.global.exception.response.ServiceErrorResponse;
import oo.kr.shared.global.exception.response.ValidationErrorResponse;
import oo.kr.shared.global.exception.type.RainCatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({RainCatchException.class})
  public ResponseEntity<ErrorResponse> handleServiceAndValidateException(RainCatchException ex) {
    ErrorResponse response = ServiceErrorResponse.create(ex);
    return ResponseEntity.status(response.getHttpStatus()).body(response);
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    ErrorResponse response = ValidationErrorResponse.create(HttpStatus.BAD_REQUEST,
        ex.getBindingResult());
    return ResponseEntity.status(response.getHttpStatus()).body(response);
  }

  @ExceptionHandler({CallNotPermittedException.class})
  public ResponseEntity<ErrorResponse> handleCircuitBreakerException(CallNotPermittedException ex) {
    ErrorResponse response = ExternalAPIErrorResponse.create(HttpStatus.SERVICE_UNAVAILABLE,
        "결제서버에 장애가 발생하였습니다.");
    return ResponseEntity.status(response.getHttpStatus()).body(response);
  }
}