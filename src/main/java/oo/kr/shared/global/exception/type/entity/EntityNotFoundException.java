package oo.kr.shared.global.exception.type.entity;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.ServiceException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ServiceException {

  public EntityNotFoundException() {
    super(HttpStatus.NOT_FOUND, new ErrorDetail("Entity가 존재하지 않습니다."));
  }
}
