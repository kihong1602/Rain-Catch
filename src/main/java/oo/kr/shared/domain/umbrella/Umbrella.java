package oo.kr.shared.domain.umbrella;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.global.utils.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Umbrella extends BaseEntity {

  @Enumerated(EnumType.STRING)
  private UmbrellaStatus umbrellaStatus;

  public void changeStatus(UmbrellaStatus status) {
    umbrellaStatus = status;
  }
}