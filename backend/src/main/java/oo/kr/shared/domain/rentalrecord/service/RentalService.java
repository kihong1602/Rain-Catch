package oo.kr.shared.domain.rentalrecord.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.controller.request.RequiredPaymentData;
import oo.kr.shared.domain.payment.domain.Payment;
import oo.kr.shared.domain.payment.domain.repository.PaymentRepository;
import oo.kr.shared.domain.rentalrecord.controller.response.UmbrellaInfo;
import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;
import oo.kr.shared.domain.rentalrecord.domain.RentalStatus;
import oo.kr.shared.domain.rentalrecord.domain.repository.RentalRecordRepository;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.umbrella.domain.Umbrella;
import oo.kr.shared.domain.umbrella.domain.repository.UmbrellaRepository;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.domain.user.domain.repository.UserRepository;
import oo.kr.shared.global.exception.type.entity.EntityNotFoundException;
import oo.kr.shared.global.exception.type.rental.UmbrellaAlreadyRentedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RentalService {

  private final RentalRecordRepository rentalRecordRepository;
  private final PaymentRepository paymentRepository;
  private final UmbrellaRepository umbrellaRepository;
  private final UserRepository userRepository;

  @Transactional
  public void saveRentalRecordAndPayment(String email, Long umbrellaId, RequiredPaymentData paymentData) {
    User user = userRepository.findByEmail(email)
                              .orElseThrow(EntityNotFoundException::new);
    Umbrella umbrella = getUmbrella(umbrellaId);
    RentalStation rentalStation = umbrella.getCurrentStation();
    umbrella.rent();
    Payment payment = savePayment(paymentData, user);
    RentalRecord rentalRecord = RentalRecord.builder()
                                            .payment(payment)
                                            .umbrella(umbrella)
                                            .rentalStation(rentalStation)
                                            .build();
    rentalRecordRepository.save(rentalRecord);
  }

  @Transactional(readOnly = true)
  public UmbrellaInfo findUmbrellaInfo(Long umbrellaId) {
    Umbrella umbrella = getUmbrella(umbrellaId);
    return UmbrellaInfo.create(umbrella);
  }

  @Transactional(readOnly = true)
  public boolean findCurrentRentalByUser(String email) {
    RentalRecord recentRentalRecord = rentalRecordRepository.findRecentRentalRecordByEmail(email);
    if (Objects.isNull(recentRentalRecord)) {
      return true;
    }
    RentalStatus rentalStatus = recentRentalRecord.getRentalStatus();
    if (rentalStatus.isReturned()) {
      return true;
    } else {
      throw new UmbrellaAlreadyRentedException();
    }
  }

  private Umbrella getUmbrella(Long umbrellaId) {
    return umbrellaRepository.findByUmbrellaId(umbrellaId)
                             .orElseThrow(EntityNotFoundException::new);
  }

  private Payment savePayment(RequiredPaymentData paymentData, User user) {
    Payment payment = Payment.create(paymentData, user);
    return paymentRepository.save(payment);
  }

}