package oo.kr.shared.global.portone;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PortOneAccessToken(
    @JsonProperty("access_token")
    String accessToken
) {

}
