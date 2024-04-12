package oo.kr.shared.global.utils;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class GeometryUtil {

  private GeometryUtil() throws IllegalAccessException {
    throw new IllegalAccessException(GeometryUtil.class.getName() + " is Utility Class");
  }

  public static Point createPoint(double latitude, double longitude) {
    String pointFormat = String.format("POINT(%f %f)", latitude, longitude);
    try {
      return (Point) new WKTReader().read(pointFormat);
    } catch (ParseException ex) {
      ex.printStackTrace();
    }
    throw new RuntimeException();
  }

  public static Location calculate(Double baseLatitude, Double baseLongitude, Double distance,
      Double bearing) {
    Double radianLatitude = toRadian(baseLatitude);
    Double radianLongitude = toRadian(baseLongitude);
    Double radianAngle = toRadian(bearing);
    double distanceRadius = distance / 6371.01;

    double latitude = Math.asin(
        sin(radianLatitude) * cos(distanceRadius) + cos(radianLatitude) * sin(distanceRadius) * cos(radianAngle));
    double longitude = radianLongitude + Math.atan2(sin(radianAngle) * sin(distanceRadius) * cos(radianLatitude),
        cos(distanceRadius) - sin(radianLatitude) * sin(latitude));

    longitude = normalizeLongitude(longitude);
    return new Location(toDegree(latitude), toDegree(longitude));
  }

  private static Double toRadian(Double coordinate) {
    return coordinate * Math.PI / 180.0;
  }

  private static Double toDegree(Double coordinate) {
    return coordinate * 180.0 / Math.PI;
  }

  private static Double sin(Double coordinate) {
    return Math.sin(coordinate);
  }

  private static Double cos(Double coordinate) {
    return Math.cos(coordinate);
  }

  private static Double normalizeLongitude(Double longitude) {
    return (longitude + 540) % 360 - 180;
  }
}