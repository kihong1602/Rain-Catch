package oo.kr.shared.domain.rentalrecord.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public record OverDueCheck(
    @JsonProperty("over_due_check")
    OverDue overDue,

    @JsonProperty("minutes_diff")
    Long minutesDiff,

    @JsonProperty("overdue_charge")
    Long overDueCharge
) {


  public static OverDueCheck create(Long minutesDiff, Long overDueCharge) {
    OverDue overDue =
        Objects.nonNull(minutesDiff) && Objects.nonNull(overDueCharge) ? OverDue.OVER_DUE : OverDue.ON_TIME;
    return new OverDueCheck(overDue, minutesDiff, overDueCharge);
  }

  public enum OverDue {
    OVER_DUE, ON_TIME
  }
}