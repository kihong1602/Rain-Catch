package oo.kr.shared.domain.umbrella.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.umbrella.domain.Umbrella;
import oo.kr.shared.domain.umbrella.domain.UmbrellaStatus;
import oo.kr.shared.domain.umbrella.domain.repository.UmbrellaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UmbrellaService {

  private final UmbrellaRepository umbrellaRepository;

  @Transactional
  public void saveUmbrella() {
    Umbrella umbrella = Umbrella.create();
    umbrellaRepository.save(umbrella);
  }

  @Transactional
  public void editUmbrellaStatus(Long id, UmbrellaStatus umbrellaStatus) {
    Umbrella umbrella = umbrellaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    umbrella.changeStatus(umbrellaStatus);
    umbrellaRepository.save(umbrella);
  }

}