package oo.kr.shared.global.portone;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenRequest(
    @JsonProperty("imp_key")
    String impKey,
    
    @JsonProperty("imp_secret")
    String impSecret
) {

}
