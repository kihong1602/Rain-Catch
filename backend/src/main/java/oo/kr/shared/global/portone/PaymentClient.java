package oo.kr.shared.global.portone;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oo.kr.shared.global.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentClient {

  private final RestClient restClient;
  private final JsonUtils jsonUtils;

  @Value("${port-one.imp-key}")
  private String impKey;
  @Value("${port-one.imp-secret}")
  private String impSecret;

  @CircuitBreaker(name = "PaymentClient")
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
                                     .retrieve()
                                     .body(PaymentResult.class);
    return jsonUtils.convertValue(result.response(), PreRegisterPaymentData.class);
  }

  @CircuitBreaker(name = "PaymentClient")
  public SinglePaymentInfo findSinglePaymentInfo(String impUid) {
    String accessToken = getAccessToken();
    String requestUri = "/payments/" + impUid;
    PaymentResult result = restClient.get()
                                     .uri(requestUri)
                                     .headers(header -> {
                                       header.add(HttpHeaders.AUTHORIZATION, accessToken);
                                       header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                                     })
                                     .retrieve()
                                     .body(PaymentResult.class);
    return jsonUtils.convertValue(result.response(), SinglePaymentInfo.class);
  }

  @CircuitBreaker(name = "PaymentClient")
  private String getAccessToken() {
    AccessTokenRequest tokenRequest = new AccessTokenRequest(impKey, impSecret);
    String requestBody = jsonUtils.serializeObjectToJson(tokenRequest);
    PaymentResult result = restClient.post()
                                     .uri("/users/getToken")
                                     .body(requestBody)
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .retrieve()
                                     .body(PaymentResult.class);
    PortOneAccessToken portOneAccessToken = jsonUtils.convertValue(result.response(), PortOneAccessToken.class);
    return portOneAccessToken.accessToken();
  }

}