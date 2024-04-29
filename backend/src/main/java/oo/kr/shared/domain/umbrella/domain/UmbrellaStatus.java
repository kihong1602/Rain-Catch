package oo.kr.shared.domain.umbrella.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UmbrellaStatus {
  WAIT("대기"),
  AVAILABLE("사용가능"),
  RENTED("대여"),
  LOST("분실"),
  DAMAGED("파손");

  private static final Map<String, UmbrellaStatus> UMBRELLA_STATUS_MAP =
      Arrays.stream(values()).collect(Collectors.toMap(UmbrellaStatus::getDescription, Function.identity()));
  private final String description;

  @JsonCreator
  public UmbrellaStatus find(String description) {
    return UMBRELLA_STATUS_MAP.get(description);
  }
}