package oo.kr.shared.domain.rentalstation;

import jakarta.persistence.Entity;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.global.utils.BaseEntity;
import oo.kr.shared.global.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RentalStation extends BaseEntity {

  private String name;
  private String address;
  private Integer availableUmbrellas;
  private Point point;

  public RentalStation(String name, String address, Integer availableUmbrellas, Double latitude, Double longitude) {
    this.name = name;
    this.address = address;
    this.availableUmbrellas = Objects.requireNonNullElse(availableUmbrellas, 0);
    this.point = GeometryUtil.createPoint(latitude, longitude);
  }

  public void increaseUmbrella() {
    availableUmbrellas++;
  }

  public void decreaseUmbrella() {
    availableUmbrellas--;
  }

}