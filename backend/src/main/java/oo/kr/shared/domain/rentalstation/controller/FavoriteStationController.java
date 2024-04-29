package oo.kr.shared.domain.rentalstation.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalstation.controller.request.FavoriteStationInfo;
import oo.kr.shared.domain.rentalstation.controller.response.RentalStationInfo;
import oo.kr.shared.domain.rentalstation.service.FavoriteStationService;
import oo.kr.shared.global.type.ResponseType;
import oo.kr.shared.global.type.SimpleResponse;
import oo.kr.shared.global.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteStationController {

  private final FavoriteStationService favoriteStationService;

  /**
   * 즐겨찾기 대여소 목록 조회 API
   */
  @GetMapping
  public ResponseEntity<List<RentalStationInfo>> findFavoriteStationInfo() {
    String email = SecurityUtils.getAuthenticationPrincipal();
    List<RentalStationInfo> favoriteStations = favoriteStationService.findFavoriteStations(email);
    return ResponseEntity.ok(favoriteStations);
  }

  /**
   * 대여소 즐겨찾기 등록 API
   */
  @PostMapping
  public ResponseEntity<SimpleResponse> saveFavoriteStation(@RequestBody FavoriteStationInfo favoriteStationInfo) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    favoriteStationService.saveFavoriteStation(email, favoriteStationInfo.id());
    return ResponseEntity.ok(new SimpleResponse(ResponseType.SUCCESS));
  }

  /**
   * 대여소 즐겨찾기 해제 API
   */
  @DeleteMapping
  public ResponseEntity<SimpleResponse> removeFavoriteStation(@RequestBody FavoriteStationInfo favoriteStationInfo) {
    String email = SecurityUtils.getAuthenticationPrincipal();
    ResponseType responseType = favoriteStationService.removeFavoriteStation(email, favoriteStationInfo.id());
    return ResponseEntity.ok(new SimpleResponse(responseType));
  }
}