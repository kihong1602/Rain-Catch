package oo.kr.shared.domain.rentalrecord.domain.repository.custom;

import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;

public interface QRentalRecordRepository {

  RentalRecord findRecentRentalRecordByEmail(String email);

  RentalRecord findRecentRentalRecordByEmailWithUmbrella(String email);
}
