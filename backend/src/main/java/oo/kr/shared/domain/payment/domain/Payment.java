package oo.kr.shared.domain.payment.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentData;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.global.type.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String impUid;

  @Column(nullable = false, unique = true)
  private String merchantUid;

  @Column(nullable = false)
  private Integer amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus paymentStatus = PaymentStatus.COMPLETED;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Payment(String impUid, String merchantUid, Integer amount, User user) {
    this.impUid = impUid;
    this.merchantUid = merchantUid;
    this.amount = amount;
    this.user = user;
  }

  public static Payment create(RequiredPaymentData paymentData, User user) {
    return new Payment(paymentData.impUid(), paymentData.merchantUid(), paymentData.amount(), user);
  }
}