package oo.kr.shared.global.exception.type.json;

import oo.kr.shared.global.exception.response.ErrorDetail;
import oo.kr.shared.global.exception.type.ServiceException;
import org.springframework.http.HttpStatus;

public class JsonSerializationFailedException extends ServiceException {

  public JsonSerializationFailedException() {
    super(HttpStatus.CONFLICT, new ErrorDetail("JSON 직렬화에 실패하였습니다."));
  }
}
