package oo.kr.shared.domain.umbrella.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.umbrella.controller.request.EditUmbrellaStatus;
import oo.kr.shared.domain.umbrella.service.UmbrellaService;
import oo.kr.shared.global.type.ResponseType;
import oo.kr.shared.global.type.SimpleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/umbrellas")
@RequiredArgsConstructor
public class UmbrellaController {

  private final UmbrellaService umbrellaService;

  /**
   * 우산 등록 API
   */
  @PostMapping
  public ResponseEntity<SimpleResponse> saveUmbrella() {
    umbrellaService.saveUmbrella();
    return ResponseEntity.ok(new SimpleResponse(ResponseType.SUCCESS));
  }

  /**
   * 우산 상태 수정 API (관리자)
   */
  @PatchMapping("/{id}")
  public ResponseEntity<SimpleResponse> editUmbrellaStatus(@PathVariable("id") Long id,
      @RequestBody EditUmbrellaStatus editUmbrellaStatus) {
    umbrellaService.editUmbrellaStatus(id, editUmbrellaStatus.umbrellaStatus());
    return ResponseEntity.ok(new SimpleResponse(ResponseType.SUCCESS));
  }
}