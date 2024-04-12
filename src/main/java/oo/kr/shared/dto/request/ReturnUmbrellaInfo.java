package oo.kr.shared.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;

public class ReturnUmbrellaInfo {

  @JsonProperty("station_id")
  private Long stationId;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  @JsonProperty("return_time")
  private LocalDateTime returnTime;

  public Long getStationId() {
    return stationId;
  }

  public LocalDateTime getReturnTime() {
    return returnTime;
  }
}