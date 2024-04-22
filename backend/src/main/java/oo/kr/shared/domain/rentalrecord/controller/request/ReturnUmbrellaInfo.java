package oo.kr.shared.domain.rentalrecord.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;


public record ReturnUmbrellaInfo(
    @JsonProperty("station_id")
    Long stationId,

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("return_time")
    LocalDateTime returnTime
) {

}