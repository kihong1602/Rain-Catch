package oo.kr.shared.domain.umbrella;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import oo.kr.shared.global.utils.BaseEntity;

@Entity
public class Umbrella extends BaseEntity {

  @Enumerated(EnumType.STRING)
  private UmbrellaStatus umbrellaStatus;

  protected Umbrella() {
  }

  public void changeStatus(UmbrellaStatus status) {
    umbrellaStatus = status;
  }
}