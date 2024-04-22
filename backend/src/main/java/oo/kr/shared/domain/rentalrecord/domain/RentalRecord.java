package oo.kr.shared.domain.rentalrecord.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.domain.payment.domain.Payment;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.umbrella.domain.Umbrella;
import oo.kr.shared.global.type.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RentalRecord extends BaseEntity {

  @Column(nullable = false, columnDefinition = "datetime")
  private LocalDateTime rentalTime;

  @Column(columnDefinition = "datetime")
  private LocalDateTime returnTime;

  @Column(nullable = false, columnDefinition = "datetime")
  private LocalDateTime expectedReturnTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RentalStatus rentalStatus = RentalStatus.RENTED;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "payment_id")
  private Payment payment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "umbrella_id")
  private Umbrella umbrella;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rental_station_id")
  private RentalStation rentalStation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "return_station_id")
  private RentalStation returnStation;

  @Builder
  private RentalRecord(Payment payment, Umbrella umbrella, RentalStation rentalStation) {
    this.rentalTime = payment.getCreateDate();
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