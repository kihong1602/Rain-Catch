package oo.kr.shared.global.exception.type.json;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.ServiceException;
import org.springframework.http.HttpStatus;

public class JsonDeserializationFailedException extends ServiceException {

  public JsonDeserializationFailedException(Throwable ex) {
    super(HttpStatus.CONFLICT, new ErrorDetail("JSON 역직렬화에 실패하였습니다."), ex);
  }
}