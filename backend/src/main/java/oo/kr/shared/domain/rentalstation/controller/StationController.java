package oo.kr.shared.domain.rentalstation.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalstation.controller.request.SaveStationInfo;
import oo.kr.shared.domain.rentalstation.controller.response.NearRentalStation;
import oo.kr.shared.domain.rentalstation.controller.response.RentalStationInfo;
import oo.kr.shared.domain.rentalstation.service.StationService;
import oo.kr.shared.global.type.Location;
import oo.kr.shared.global.type.ResponseType;
import oo.kr.shared.global.type.SimpleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {

  private final StationService stationService;

  /**
   * 반납시 우산대여소 정보 조회 API
   */
  @GetMapping("/{id}")
  public ResponseEntity<RentalStationInfo> findRentalStation(@PathVariable("id") Long id) {
    RentalStationInfo stationInfo = stationService.findStationInfo(id);
    return ResponseEntity.ok(stationInfo);
  }

  /**
   * 주변 우산대여소 위치 및 정보 조회 API
   */
  @GetMapping("/near")
  public ResponseEntity<NearRentalStation> nearStation(@ModelAttribute Location location) {
    NearRentalStation nearStation = stationService.findNearStation(location);
    return ResponseEntity.ok(nearStation);
  }

  /**
   * 우산대여소 등록 API
   */
  @PostMapping
  public ResponseEntity<SimpleResponse> saveStation(@RequestBody SaveStationInfo stationInfo) {
    stationService.saveStation(stationInfo);
    return ResponseEntity.ok(new SimpleResponse(ResponseType.SUCCESS));
  }

  /**
   * 우산 대여소 정보 수정 API
   */
  @PatchMapping("/{id}")
  public void editStationInfo(@PathVariable("id") Long id) {

  }

  /**
   * 우산 대여소 삭제 API
   */
  @DeleteMapping("/{id}")
  public void removeStation(@PathVariable("id") Long id) {

  }

}