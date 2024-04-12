package oo.kr.shared.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RequiredPaymentInfo(

    @JsonProperty("umbrella_id")
    Long umbrellaId,

    @JsonProperty("imp_uid")
    String impUid,

    @JsonProperty("merchant_uid")
    String merchantUid,

    Integer amount
) {

}