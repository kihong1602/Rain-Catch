package oo.kr.shared.domain.umbrella.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.global.type.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Umbrella extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UmbrellaStatus umbrellaStatus = UmbrellaStatus.WAIT;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "current_station")
  private RentalStation currentStation;

  public static Umbrella create() {
    return new Umbrella();
  }

  public void changeStatus(UmbrellaStatus umbrellaStatus) {
    this.umbrellaStatus = umbrellaStatus;
  }


  public void rent() {
    umbrellaStatus = UmbrellaStatus.RENTED;
    currentStation = null;
  }

  public void returnToStation(RentalStation rentalStation) {
    umbrellaStatus = UmbrellaStatus.AVAILABLE;
    currentStation = rentalStation;
  }

}