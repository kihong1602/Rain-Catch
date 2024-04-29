package oo.kr.shared.domain.rentalstation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import oo.kr.shared.domain.rentalstation.controller.response.NearRentalStation;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import oo.kr.shared.domain.rentalstation.domain.repository.RentalStationRepository;
import oo.kr.shared.global.type.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RentalStationServiceTest {

  @Autowired
  private RentalStationRepository rentalStationRepository;

  @Autowired
  private RentalStationService rentalStationService;

  @BeforeEach
  void init() {
    RentalStation rentalStation1 = new RentalStation("삼성타운아파트", "서울특별시 은평구 서오릉로94", 37.6095200026749, 126.921700201605);
    RentalStation rentalStation2 = new RentalStation("아이&유안경콘택트", "서울특별시 은평구 서오릉로 93", 37.6082331420116,
        126.920788502823);
    RentalStation rentalStation3 = new RentalStation("컴포즈커피 역촌중앙점", "서울 은평구 서오릉로 86", 37.6078717971096,
        126.921797061702);
    rentalStationRepository.saveAllAndFlush(List.of(rentalStation1, rentalStation2, rentalStation3));
  }

  @Test
  @DisplayName("위도, 경도를 입력하면 주변 500m 내의 위치정보가 반환된다.")
  void findNearStationTest() {
    //given
    Location location = new Location(37.608228287698275, 126.92243283200172);

    //when
    NearRentalStation nearestStation = rentalStationService.findNearStation(location);

    //then
    assertThat(nearestStation.rentalStationDataList()).hasSize(3)
                                                      .extracting("name", "address")
                                                      .containsExactly(
                                                          tuple("삼성타운아파트", "서울특별시 은평구 서오릉로94"),
                                                          tuple("아이&유안경콘택트", "서울특별시 은평구 서오릉로 93"),
                                                          tuple("컴포즈커피 역촌중앙점", "서울 은평구 서오릉로 86")
                                                      );
  }
}