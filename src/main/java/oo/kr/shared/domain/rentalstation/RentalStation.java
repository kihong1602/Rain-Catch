package oo.kr.shared.domain.rentalstation;

import java.util.Objects;
import javax.persistence.Entity;
import oo.kr.shared.global.utils.BaseEntity;
import oo.kr.shared.global.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;

@Entity
public class RentalStation extends BaseEntity {

  private String name;
  private String address;
  private Integer availableUmbrellas;
  private Point point;

  protected RentalStation() {
  }

  public RentalStation(String name, String address, Integer availableUmbrellas, Double latitude, Double longitude) {
    this.name = name;
    this.address = address;
    this.availableUmbrellas = Objects.requireNonNullElse(availableUmbrellas, 0);
    this.point = GeometryUtil.createPoint(latitude, longitude);
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public Integer getAvailableUmbrellas() {
    return availableUmbrellas;
  }

  public Point getPoint() {
    return point;
  }

  public void increaseUmbrella() {
    availableUmbrellas++;
  }

  public void decreaseUmbrella() {
    availableUmbrellas--;
  }

}