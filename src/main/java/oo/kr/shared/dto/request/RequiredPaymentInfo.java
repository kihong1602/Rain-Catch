package oo.kr.shared.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequiredPaymentInfo {

  @JsonProperty("station_id")
  private Long stationId;

  @JsonProperty("umbrella_id")
  private Long umbrellaId;

  @JsonProperty("imp_uid")
  private String impUid;

  @JsonProperty("merchant_uid")
  private String merchantUid;
  
  private Integer amount;

  public Long getStationId() {
    return stationId;
  }

  public Long getUmbrellaId() {
    return umbrellaId;
  }

  public String getImpUid() {
    return impUid;
  }

  public String getMerchantUid() {
    return merchantUid;
  }

  public Integer getAmount() {
    return amount;
  }
}