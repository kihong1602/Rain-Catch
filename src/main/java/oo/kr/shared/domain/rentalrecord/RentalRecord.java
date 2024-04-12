package oo.kr.shared.domain.rentalrecord;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import oo.kr.shared.domain.payment.Payment;
import oo.kr.shared.domain.rentalstation.RentalStation;
import oo.kr.shared.domain.umbrella.Umbrella;
import oo.kr.shared.domain.umbrella.UmbrellaStatus;
import oo.kr.shared.global.utils.BaseEntity;

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

  protected RentalRecord() {
  }

  public RentalRecord(LocalDateTime rentalTime, Payment payment, Umbrella umbrella, RentalStation rentalStation) {
    this.rentalTime = rentalTime;
    this.expectedReturnTime = rentalTime.plusDays(1L);
    this.payment = payment;
    this.umbrella = umbrella;
    this.rentalStation = rentalStation;
  }

  public LocalDateTime getRentalTime() {
    return rentalTime;
  }

  public LocalDateTime getReturnTime() {
    return returnTime;
  }

  public LocalDateTime getExpectedReturnTime() {
    return expectedReturnTime;
  }

  public RentalStatus getRentalStatus() {
    return rentalStatus;
  }

  public Payment getPayment() {
    return payment;
  }

  public Umbrella getUmbrella() {
    return umbrella;
  }

  public RentalStation getRentalStation() {
    return rentalStation;
  }

  public RentalStation getReturnStation() {
    return returnStation;
  }

  public void returnUmbrella(RentalStation returnStation, LocalDateTime returnTime) {
    this.returnStation = returnStation;
    this.returnTime = returnTime;
    this.rentalStatus = RentalStatus.RETURN;
    this.returnStation.increaseUmbrella();
    this.umbrella.changeStatus(UmbrellaStatus.AVAILABLE);
  }
}