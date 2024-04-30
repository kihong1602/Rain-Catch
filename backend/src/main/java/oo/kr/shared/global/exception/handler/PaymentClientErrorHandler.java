package oo.kr.shared.global.exception.handler;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.exception.type.RainCatchException;
import oo.kr.shared.global.exception.type.payment.APIResponseNotFoundException;
import oo.kr.shared.global.exception.type.payment.APIServerErrorException;
import oo.kr.shared.global.exception.type.payment.InvalidAccessTokenException;
import oo.kr.shared.global.exception.type.payment.InvalidImpUidException;
import oo.kr.shared.global.exception.type.payment.OtherHttpStatus4xxException;
import oo.kr.shared.global.portone.PaymentResult;
import oo.kr.shared.global.utils.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ExtractingResponseErrorHandler;

@Component
@RequiredArgsConstructor
public class PaymentClientErrorHandler extends ExtractingResponseErrorHandler {

  private final JsonUtils jsonUtils;

  @Override
  protected boolean hasError(HttpStatusCode statusCode) {
    return statusCode.is4xxClientError() || statusCode.is5xxServerError();
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    try {
      HttpStatusCode statusCode = response.getStatusCode();
      if (statusCode.is5xxServerError()) {
        throw new APIServerErrorException();
      }

      if (statusCode.is4xxClientError()) {
        PaymentResult result = jsonUtils.deserializeJsonToObject(response.getBody(), PaymentResult.class);
        exceptionByStatusCode(statusCode, result);
      }
    } catch (IOException e) {
      throw new APIResponseNotFoundException(e);
    }
    super.handleError(response);
  }

  private void exceptionByStatusCode(HttpStatusCode statusCode, PaymentResult result)
      throws RainCatchException {
    String message = result.message();
    if (statusCode.isSameCodeAs(HttpStatus.UNAUTHORIZED)) {
      throw new InvalidAccessTokenException(message);
    }
    if (statusCode.isSameCodeAs(HttpStatus.NOT_FOUND)) {
      throw new InvalidImpUidException(message);
    }
    throw new OtherHttpStatus4xxException(message);
  }

}
