package oo.kr.shared.domain.payment.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentInfo;
import oo.kr.shared.domain.payment.domain.Payment;
import oo.kr.shared.domain.payment.domain.repository.PaymentRepository;
import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;
import oo.kr.shared.domain.rentalrecord.domain.repository.RentalRecordRepository;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.umbrella.domain.Umbrella;
import oo.kr.shared.domain.umbrella.domain.UmbrellaStatus;
import oo.kr.shared.domain.umbrella.domain.repository.UmbrellaRepository;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.domain.user.domain.repository.UserRepository;
import oo.kr.shared.global.exception.type.entity.EntityNotFoundException;
import oo.kr.shared.global.security.auth.SecurityUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentService {

  private final UserRepository userRepository;
  private final PaymentRepository paymentRepository;
  private final UmbrellaRepository umbrellaRepository;
  private final RentalRecordRepository rentalRecordRepository;

  @Transactional
  public void completedPayment(RequiredPaymentInfo paymentInfo, SecurityUserInfo userInfo) {
    User user = userRepository.findById(userInfo.id())
                              .orElseThrow(EntityNotFoundException::new);
    Payment payment = savePayment(paymentInfo, user);
    Payment savePayment = paymentRepository.save(payment);
    Umbrella umbrella = umbrellaRepository.findById(paymentInfo.umbrellaId())
                                          .orElseThrow(EntityNotFoundException::new);
    RentalStation rentalStation = rentUmbrella(umbrella);
    saveRentalRecord(savePayment.getCreateDate(), payment, umbrella, rentalStation);
  }

  private Payment savePayment(RequiredPaymentInfo paymentInfo, User user) {
    Payment payment = new Payment(paymentInfo.impUid(), paymentInfo.merchantUid(), paymentInfo.amount(), user);
    return paymentRepository.save(payment);
  }

  private RentalStation rentUmbrella(Umbrella umbrella) {
    RentalStation rentalStation = umbrella.getCurrentStation();
    umbrella.changeStatus(UmbrellaStatus.RENTED);
    umbrella.rent();
    umbrellaRepository.save(umbrella);
    return rentalStation;
  }

  private void saveRentalRecord(LocalDateTime rentalTime, Payment payment, Umbrella umbrella,
      RentalStation rentalStation) {
    RentalRecord rentalRecord = new RentalRecord(rentalTime, payment, umbrella, rentalStation);
    rentalRecordRepository.save(rentalRecord);
  }
}