package oo.kr.shared.domain.rentalrecord.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.domain.Payment;
import oo.kr.shared.domain.payment.domain.repository.PaymentRepository;
import oo.kr.shared.domain.rentalrecord.controller.request.ReturnUmbrellaInfo;
import oo.kr.shared.domain.rentalrecord.controller.response.OverDueCheck;
import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;
import oo.kr.shared.domain.rentalrecord.domain.repository.RentalRecordRepository;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.rentalstation.domain.repository.RentalStationRepository;
import oo.kr.shared.global.exception.type.entity.EntityNotFoundException;
import oo.kr.shared.global.security.auth.SecurityUserInfo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RentalService {

  private final RentalRecordRepository rentalRecordRepository;
  private final RentalStationRepository rentalStationRepository;
  private final PaymentRepository paymentRepository;

  @Transactional(readOnly = true)
  public OverDueCheck overdueCheck(SecurityUserInfo userInfo) {
    RentalRecord rentalRecord = getRentalRecord(userInfo);
    LocalDateTime expectedReturnTime = rentalRecord.getExpectedReturnTime();
    LocalDateTime now = LocalDateTime.now();
    long minutesDiff = Duration.between(expectedReturnTime, now)
                               .toMinutes();
    long additionalCharge = 0L;
    if (minutesDiff > 0) {
      double chargePeriods = Math.ceil((double) minutesDiff / 10);
      additionalCharge = (long) chargePeriods * 100;
    }
    return OverDueCheck.create(minutesDiff, additionalCharge);
  }

  @Transactional
  public void returnUmbrella(ReturnUmbrellaInfo returnUmbrellaInfo, SecurityUserInfo userInfo) {
    RentalRecord rentalRecord = getRentalRecord(userInfo);
    RentalStation rentalStation = rentalStationRepository.findById(returnUmbrellaInfo.stationId())
                                                         .orElseThrow(EntityNotFoundException::new);
    rentalRecord.returnUmbrella(rentalStation, returnUmbrellaInfo.returnTime());
    rentalRecord.getUmbrella()
                .returnToStation(rentalStation);
    rentalRecordRepository.save(rentalRecord);
  }

  private Payment getLastPayment(Long id) {
    Pageable pageable = PageRequest.of(0, 1);
    List<Payment> payments = paymentRepository.findByUserId(id, pageable);
    return Objects.requireNonNull(payments.get(0));
  }

  private RentalRecord getRentalRecord(SecurityUserInfo userInfo) {
    Payment lastPayment = getLastPayment(userInfo.id());
    return rentalRecordRepository.findByUmbrellaIdAndPaymentId(lastPayment.getId())
                                 .orElseThrow(EntityNotFoundException::new);
  }
}