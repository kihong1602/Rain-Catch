package oo.kr.shared.global.portone;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.global.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class PaymentClient {

  private final RestClient restClient;
  private final JsonUtils jsonUtils;

  @Value("${port-one.imp-key}")
  private String impKey;
  @Value("${port-one.imp-secret}")
  private String impSecret;

  public PreRegisterPaymentData preRegister(PreRegisterPaymentData paymentData) {
    String accessToken = getAccessToken();
    String requestBody = jsonUtils.serializeObjectToJson(paymentData);
    PaymentResult result = restClient.post()
                                     .uri("/payments/prepare")
                                     .headers(header -> {
                                       header.add(HttpHeaders.AUTHORIZATION, accessToken);
                                       header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                                     })
                                     .body(requestBody)
                                     .exchange((req, res) -> checkResponse(res));
    return jsonUtils.convertValue(result.response(), PreRegisterPaymentData.class);
  }

  public SinglePaymentInfo findSinglePaymentInfo(String impUid) {
    String accessToken = getAccessToken();
    String requestUri = "/payments/" + impUid;
    PaymentResult result = restClient.get()
                                     .uri(requestUri)
                                     .headers(header -> {
                                       header.add(HttpHeaders.AUTHORIZATION, accessToken);
                                       header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                                     })
                                     .exchange((req, res) -> checkResponse(res));
    return jsonUtils.convertValue(result.response(), SinglePaymentInfo.class);
  }

  private String getAccessToken() {
    AccessTokenRequest tokenRequest = new AccessTokenRequest(impKey, impSecret);
    String requestBody = jsonUtils.serializeObjectToJson(tokenRequest);
    PaymentResult result = restClient.post()
                                     .uri("/users/getToken")
                                     .body(requestBody)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .exchange((req, res) -> checkResponse(res));
    PortOneAccessToken portOneAccessToken = jsonUtils.convertValue(result.response(), PortOneAccessToken.class);
    return portOneAccessToken.accessToken();
  }

  private PaymentResult checkResponse(ClientHttpResponse response) {
    try {
      HttpStatusCode statusCode = response.getStatusCode();
      if (statusCode.is5xxServerError()) {
        // 여기선 500에러에 대한 예외 추가 설정
        throw new RuntimeException();
      }
      PaymentResult result = jsonUtils.deserializeJsonToObject(response.getBody(), PaymentResult.class);
      if (statusCode.is4xxClientError()) {
        // 여기선 이유 설정
      }
      return result;

    } catch (IOException e) {
      // 여기선 응답자체가 안왔다는 예외 추가
      throw new RuntimeException(e);
    }
  }

}