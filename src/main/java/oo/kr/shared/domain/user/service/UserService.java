package oo.kr.shared.domain.user.service;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;
import oo.kr.shared.domain.rentalrecord.domain.repository.RentalRecordRepository;
import oo.kr.shared.domain.user.controller.request.SignupInfo;
import oo.kr.shared.domain.user.controller.response.DuplicateEmailResult;
import oo.kr.shared.domain.user.controller.response.RentalRecordData;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.domain.user.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final RentalRecordRepository rentalRecordRepository;
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public Page<RentalRecordData> viewRentalRecord(String email, Pageable pageable) {
    User user = userRepository.findByEmail(email)
                              .orElseThrow(RuntimeException::new);
    Page<RentalRecord> rentalRecords = rentalRecordRepository.findListByUserId(user.getId(), pageable);
    return rentalRecords.map(RentalRecordData::of);
  }

  public DuplicateEmailResult duplicateCheck(String email) {
    return new DuplicateEmailResult(userRepository.findByEmail(email)
                                                  .isEmpty());
  }

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
