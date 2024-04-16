package oo.kr.shared.service;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalrecord.RentalRecord;
import oo.kr.shared.domain.rentalrecord.RentalRecordRepository;
import oo.kr.shared.domain.user.User;
import oo.kr.shared.domain.user.UserRepository;
import oo.kr.shared.dto.request.SignupInfo;
import oo.kr.shared.dto.response.RentalRecordData;
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

  public void register(SignupInfo signupInfo) {
    String email = signupInfo.email();
    String password = encodingPassword(signupInfo.password());
    User user = new User(email, password);
    // 여기서 중복없는지 체크
    userRepository.save(user);
  }

  private String encodingPassword(String password) {
    return bCryptPasswordEncoder.encode(password);
  }
}
