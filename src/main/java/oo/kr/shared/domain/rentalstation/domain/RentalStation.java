package oo.kr.shared.domain.rentalstation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.domain.umbrella.domain.Umbrella;
import oo.kr.shared.global.utils.BaseEntity;
import oo.kr.shared.global.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RentalStation extends BaseEntity {

  private String name;
  private String address;
  @OneToMany(mappedBy = "currentStation")
  private List<Umbrella> availableUmbrellas = new ArrayList<>();
  private Point point;

  public RentalStation(String name, String address, Double latitude, Double longitude) {
    this.name = name;
    this.address = address;
    this.point = GeometryUtil.createPoint(latitude, longitude);
  }

}