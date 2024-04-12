package oo.kr.shared.domain.rentalrecord;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.domain.payment.Payment;
import oo.kr.shared.domain.rentalstation.RentalStation;
import oo.kr.shared.domain.umbrella.Umbrella;
import oo.kr.shared.global.utils.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RentalRecord extends BaseEntity {

  @Column(columnDefinition = "datetime")
  private LocalDateTime rentalTime;

  @Column(columnDefinition = "datetime")
  private LocalDateTime returnTime;

  @Column(columnDefinition = "datetime")
  private LocalDateTime expectedReturnTime;

  @Enumerated(EnumType.STRING)
  private RentalStatus rentalStatus = RentalStatus.RENTED;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  private Payment payment;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "umbrella_id")
  private Umbrella umbrella;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_station_id")
  private RentalStation rentalStation;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  @JoinColumn(name = "return_station_id")
  private RentalStation returnStation;

  public RentalRecord(LocalDateTime rentalTime, Payment payment, Umbrella umbrella, RentalStation rentalStation) {
    this.rentalTime = rentalTime;
    this.expectedReturnTime = rentalTime.plusDays(1L);
    this.payment = payment;
    this.umbrella = umbrella;
    this.rentalStation = rentalStation;
  }

  public void returnUmbrella(RentalStation returnStation, LocalDateTime returnTime) {
    this.returnStation = returnStation;
    this.returnTime = returnTime;
    this.rentalStatus = RentalStatus.RETURN;
  }
}