package oo.kr.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class OverDueCheck {

  @JsonProperty("over_due_check")
  private OverDue overDue;

  @JsonProperty("minutes_diff")
  private Long minutesDiff;

  @JsonProperty("overdue_charge")
  private Long overDueCharge;

  public OverDueCheck(Long minutesDiff, Long overDueCharge) {
    this.overDue = Objects.nonNull(minutesDiff) && Objects.nonNull(overDueCharge) ? OverDue.OVER_DUE : OverDue.ON_TIME;
    this.minutesDiff = minutesDiff;
    this.overDueCharge = overDueCharge;
  }

  public enum OverDue {
    OVER_DUE, ON_TIME
  }
}