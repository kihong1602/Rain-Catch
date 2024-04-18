package oo.kr.shared.global.portone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentData;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SinglePaymentInfo(
    @JsonProperty("imp_uid")
    String impUid,

    @JsonProperty("merchant_uid")
    String merchantUid,

    Integer amount
) {

  public boolean isEquals(RequiredPaymentData paymentData) {
    Integer requiredAmount = paymentData.amount();
    String requiredImpUid = paymentData.impUid();
    String requiredMerchantUid = paymentData.merchantUid();
    return impUid.equals(requiredImpUid) && merchantUid.equals(requiredMerchantUid) && amount.equals(requiredAmount);
  }
}