package oo.kr.shared.domain.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.domain.user.User;
import oo.kr.shared.global.utils.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment extends BaseEntity {

  private String impUid;

  private String merchantUid;

  private Integer amount;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus = PaymentStatus.COMPLETED;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private User user;

  public Payment(String impUid, String merchantUid, Integer amount, User user) {
    this.impUid = impUid;
    this.merchantUid = merchantUid;
    this.amount = amount;
    this.user = user;
  }

}