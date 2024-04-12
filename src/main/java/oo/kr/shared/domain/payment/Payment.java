package oo.kr.shared.domain.payment;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import oo.kr.shared.domain.member.Member;
import oo.kr.shared.global.utils.BaseEntity;

@Entity
public class Payment extends BaseEntity {

  private String impUid;

  private String merchantUid;

  private Integer amount;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus = PaymentStatus.COMPLETED;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  protected Payment() {
  }

  public Payment(String impUid, String merchantUid, Integer amount, Member member) {
    this.impUid = impUid;
    this.merchantUid = merchantUid;
    this.amount = amount;
    this.member = member;
  }

  public String getImpUid() {
    return impUid;
  }

  public String getMerchantUid() {
    return merchantUid;
  }

  public Integer getAmount() {
    return amount;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public Member getMember() {
    return member;
  }
}