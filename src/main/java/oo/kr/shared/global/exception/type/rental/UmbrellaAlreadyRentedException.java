package oo.kr.shared.global.exception.type.rental;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.ServiceException;
import org.springframework.http.HttpStatus;

public class UmbrellaAlreadyRentedException extends ServiceException {

  public UmbrellaAlreadyRentedException() {
    super(HttpStatus.CONFLICT, new ErrorDetail("이미 대여한 우산이 존재합니다."));
  }
}
