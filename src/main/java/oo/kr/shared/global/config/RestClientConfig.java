package oo.kr.shared.global.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Value("${network.timeout.connect}")
  private int connectTimeout;
  @Value("${network.timeout.socket}")
  private int socketTimeout;
  @Value("${network.max-connection}")
  private int maxConnection;
  @Value("${network.max-per-route}")
  private int maxPerRoute;

  @Bean
  public RestClient restClient() {
    return RestClient.builder()
                     .baseUrl("https://api.iamport.kr")
                     .requestFactory(httpRequestFactory())
                     .build();
  }

  private ClientHttpRequestFactory httpRequestFactory() {
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
    CloseableHttpClient httpClient = (CloseableHttpClient) setHttpClient();
    factory.setHttpClient(httpClient);
    return factory;
  }

  private HttpClient setHttpClient() {
    RequestConfig requestConfig = setRequestConfig();
    HttpClientConnectionManager connectionManager = setConnectionManager(setConnectionConfig());
    return HttpClientBuilder.create()
                            .setDefaultRequestConfig(requestConfig)
                            .setConnectionManager(connectionManager)
                            .build();
  }

  private RequestConfig setRequestConfig() {
    return RequestConfig.custom()
                        .setConnectionRequestTimeout(Timeout.ofSeconds(connectTimeout))
                        .build();
  }

  private ConnectionConfig setConnectionConfig() {
    return ConnectionConfig.custom()
                           .setConnectTimeout(Timeout.ofSeconds(connectTimeout))
                           .setSocketTimeout(Timeout.ofSeconds(socketTimeout))
                           .build();
  }

  private HttpClientConnectionManager setConnectionManager(ConnectionConfig connectionConfig) {
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setDefaultConnectionConfig(connectionConfig);
    connectionManager.setMaxTotal(maxConnection);
    connectionManager.setDefaultMaxPerRoute(maxPerRoute);
    return connectionManager;
  }
}
