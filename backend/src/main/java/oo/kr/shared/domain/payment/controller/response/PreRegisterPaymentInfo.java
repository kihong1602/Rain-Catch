package oo.kr.shared.domain.payment.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import oo.kr.shared.global.portone.PreRegisterPaymentData;

public record PreRegisterPaymentInfo(
    String email,
    @JsonProperty("payment_data")
    PreRegisterPaymentData paymentData
) {

}
