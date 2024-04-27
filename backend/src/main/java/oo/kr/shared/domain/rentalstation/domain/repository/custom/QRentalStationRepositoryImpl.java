package oo.kr.shared.domain.rentalstation.domain.repository.custom;

import static oo.kr.shared.domain.rentalstation.domain.QRentalStation.rentalStation;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QRentalStationRepositoryImpl implements QRentalStationRepository {

  private final JPAQueryFactory query;

  @Override
  public Optional<RentalStation> findRentalStationInfo(Long id) {
    RentalStation result = query.selectFrom(rentalStation)
                                .join(rentalStation.availableUmbrellas)
                                .where(rentalStation.id.eq(id))
                                .fetchOne();
    return Optional.ofNullable(result);
  }

  /**
   * 하버사인 공식을 이용해 입력받은 위도, 경도 기반 반경 500m 내의 우산 대여소를 조회합니다. <br/>:: 하버사인 공식 ::<br/> 6371 * acos(
   * cos(radians(input.latitude)) * cos(radians(station.latitude)) * cos(radians(station.longitude) -
   * radians(input.latitude)) + sin(radians(input.latitude)) * sin(radians(station.latitude))
   */
  @Override
  public List<RentalStation> findNearRentalStation(Double latitude, Double longitude) {

    NumberExpression<Double> radiansLatitude = Expressions.numberTemplate(Double.class, "radians({0})", latitude);

    NumberExpression<Double> cosLatitude = Expressions.numberTemplate(Double.class, "cos({0})", radiansLatitude);
    NumberExpression<Double> cosStationLatitude = Expressions.numberTemplate(Double.class, "cos(radians({0}))",
        rentalStation.latitude);

    NumberExpression<Double> sinLatitude = Expressions.numberTemplate(Double.class, "sin({0})", radiansLatitude);
    NumberExpression<Double> sinStationLatitude = Expressions.numberTemplate(Double.class, "sin(radians({0}))",
        rentalStation.latitude);

    NumberExpression<Double> cosLongitude = Expressions.numberTemplate(Double.class, "cos(radians({0}) - radians({1}))",
        rentalStation.longitude, longitude);

    NumberExpression<Double> expression = cosLatitude.multiply(cosStationLatitude)
                                                     .multiply(cosLongitude)
                                                     .add(sinLatitude.multiply(sinStationLatitude));

    NumberExpression<Double> acosExpression = Expressions.numberTemplate(Double.class, "acos({0})", expression);

    NumberExpression<Double> haversine = Expressions.numberTemplate(Double.class, "6371 * {0}", acosExpression);

    return query.selectFrom(rentalStation)
                .where(haversine.loe(0.5))
                .fetch();
  }


}