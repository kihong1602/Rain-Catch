package oo.kr.shared.domain.payment.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequiredPaymentData(
    @JsonProperty("imp_uid")
    String impUid,

    @JsonProperty("merchant_uid")
    String merchantUid,

    Integer amount
) {

}