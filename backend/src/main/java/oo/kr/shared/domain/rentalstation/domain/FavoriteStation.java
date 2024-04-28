package oo.kr.shared.domain.rentalstation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.global.type.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteStation extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private RentalStation rentalStation;

  public FavoriteStation(User user, RentalStation rentalStation) {
    this.user = user;
    this.rentalStation = rentalStation;
  }
}