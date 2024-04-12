package oo.kr.shared.service;

import oo.kr.shared.domain.rentalrecord.RentalRecord;
import oo.kr.shared.domain.rentalrecord.RentalRecordRepository;
import oo.kr.shared.dto.response.RentalRecordData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

  private final RentalRecordRepository rentalRecordRepository;

  public MemberService(RentalRecordRepository rentalRecordRepository) {
    this.rentalRecordRepository = rentalRecordRepository;
  }

  public Page<RentalRecordData> viewRentalRecord(Long memberId, Pageable pageable) {
    Page<RentalRecord> rentalRecords = rentalRecordRepository.findListByMemberId(memberId, pageable);
    return rentalRecords.map(RentalRecordData::of);
  }
}
