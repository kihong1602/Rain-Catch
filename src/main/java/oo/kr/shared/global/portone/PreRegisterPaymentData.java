package oo.kr.shared.global.portone;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PreRegisterPaymentData(
    @JsonProperty("merchant_uid")
    String merchantUid,
    
    Integer amount
) {

}
