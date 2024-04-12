package oo.kr.shared.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.payment.Payment;
import oo.kr.shared.domain.payment.PaymentRepository;
import oo.kr.shared.domain.rentalrecord.RentalRecord;
import oo.kr.shared.domain.rentalrecord.RentalRecordRepository;
import oo.kr.shared.domain.rentalstation.RentalStation;
import oo.kr.shared.domain.rentalstation.RentalStationRepository;
import oo.kr.shared.dto.request.ReturnUmbrellaInfo;
import oo.kr.shared.dto.response.OverDueCheck;
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
  public OverDueCheck overdueCheck(Long memberId) {
    Payment lastPayment = getLastPayment(memberId);
    RentalRecord rentalRecord = rentalRecordRepository.findByUmbrellaIdAndPaymentId(lastPayment.getId())
                                                      .orElseThrow(RuntimeException::new);
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
  public void returnUmbrella(ReturnUmbrellaInfo returnUmbrellaInfo, Long memberId) {
    Payment lastPayment = getLastPayment(memberId);
    RentalRecord rentalRecord = rentalRecordRepository.findByUmbrellaIdAndPaymentId(lastPayment.getId())
                                                      .orElseThrow(RuntimeException::new);
    RentalStation rentalStation = rentalStationRepository.findById(returnUmbrellaInfo.stationId())
                                                         .orElseThrow(RuntimeException::new);
    rentalRecord.returnUmbrella(rentalStation, returnUmbrellaInfo.returnTime());
    rentalRecordRepository.save(rentalRecord);
  }

  private Payment getLastPayment(Long memberId) {
    Pageable pageable = PageRequest.of(0, 1);
    List<Payment> payments = paymentRepository.findByMemberId(memberId, pageable);
    return Objects.requireNonNull(payments.get(0));
  }

}