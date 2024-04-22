package oo.kr.shared.domain.user.service;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;
import oo.kr.shared.domain.rentalrecord.domain.repository.RentalRecordRepository;
import oo.kr.shared.domain.user.controller.request.SignupInfo;
import oo.kr.shared.domain.user.controller.response.DuplicateEmailResult;
import oo.kr.shared.domain.user.controller.response.RentalRecordData;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.domain.user.domain.repository.UserRepository;
import oo.kr.shared.global.exception.type.entity.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

  private final RentalRecordRepository rentalRecordRepository;
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Transactional(readOnly = true)
  public Page<RentalRecordData> viewRentalRecord(String email, Pageable pageable) {
    User user = userRepository.findByEmail(email)
                              .orElseThrow(EntityNotFoundException::new);
    Page<RentalRecord> rentalRecords = rentalRecordRepository.findListByUserId(user.getId(), pageable);
    return rentalRecords.map(RentalRecordData::of);
  }

  @Transactional(readOnly = true)
  public DuplicateEmailResult duplicateCheck(String email) {
    return new DuplicateEmailResult(userRepository.findByEmail(email)
                                                  .isEmpty());
  }

  @Transactional
  public void register(SignupInfo signupInfo) {
    String email = signupInfo.email();
    String password = encodingPassword(signupInfo.password());
    User user = new User(email, password);
    userRepository.save(user);
  }

  private String encodingPassword(String password) {
    return bCryptPasswordEncoder.encode(password);
  }
}
