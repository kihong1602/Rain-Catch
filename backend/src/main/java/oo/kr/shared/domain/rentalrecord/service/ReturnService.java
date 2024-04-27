package oo.kr.shared.domain.rentalrecord.service;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalrecord.controller.request.ReturnUmbrellaInfo;
import oo.kr.shared.domain.rentalrecord.controller.response.OverDueCheck;
import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;
import oo.kr.shared.domain.rentalrecord.domain.repository.RentalRecordRepository;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.rentalstation.domain.repository.RentalStationRepository;
import oo.kr.shared.domain.umbrella.domain.Umbrella;
import oo.kr.shared.global.exception.type.entity.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReturnService {

  private static final long OVER_DUE_CHARGE = 100L;
  private static final int INTERVAL_MINUTES = 10;
  private final RentalRecordRepository rentalRecordRepository;
  private final RentalStationRepository rentalStationRepository;

  @Transactional(readOnly = true)
  public OverDueCheck overdueCheck(String email) {
    RentalRecord rentalRecord = rentalRecordRepository.findRecentRentalRecordByEmail(email);
    LocalDateTime expectedReturnTime = rentalRecord.getExpectedReturnTime();
    LocalDateTime now = LocalDateTime.now();
    long minutesDiff = Duration.between(expectedReturnTime, now)
                               .toMinutes();
    long additionalCharge = calculateCharge(minutesDiff);
    return OverDueCheck.create(minutesDiff, additionalCharge);
  }

  @Transactional
  public void returnUmbrella(ReturnUmbrellaInfo returnUmbrellaInfo, String email) {
    RentalRecord rentalRecord = rentalRecordRepository.findRecentRentalRecordByEmailWithUmbrella(email);
    RentalStation rentalStation = rentalStationRepository.findById(returnUmbrellaInfo.stationId())
                                                         .orElseThrow(EntityNotFoundException::new);
    rentalRecord.returnUmbrella(rentalStation, returnUmbrellaInfo.returnTime());
    Umbrella umbrella = rentalRecord.getUmbrella();
    umbrella.returnToStation(rentalStation);
    rentalRecordRepository.save(rentalRecord);
  }

  private long calculateCharge(long minutesDiff) {
    if (minutesDiff > 0) {
      long chargePeriods = (minutesDiff + INTERVAL_MINUTES - 1) / INTERVAL_MINUTES;
      return chargePeriods * OVER_DUE_CHARGE;
    }
    return 0L;
  }
}