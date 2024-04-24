package oo.kr.shared.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import oo.kr.shared.domain.user.controller.response.DuplicateEmailResult;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.domain.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  public void init() {
    userRepository.saveAndFlush(new User("test@test.com", "1234"));
  }

  @Test
  @DisplayName("중복된 이메일 값을 입력하면 false 반환")
  void duplicateCheckReturnFalse() {
    //given

    //when
    DuplicateEmailResult duplicateEmailResult = userService.duplicateCheck("test@test.com");
    //then
    assertThat(duplicateEmailResult.isAvailable()).isFalse();
  }
}