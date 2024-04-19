package oo.kr.shared.domain.user.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.Optional;
import oo.kr.shared.domain.payment.domain.Payment;
import oo.kr.shared.domain.payment.domain.PaymentStatus;
import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;
import oo.kr.shared.domain.rentalrecord.domain.RentalStatus;
import oo.kr.shared.domain.rentalstation.domain.RentalStation;

public record RentalRecordData(
    @JsonProperty("rental_record_info")
    RentalRecordInfo rentalRecordInfo,

    @JsonProperty("payment_info")
    PaymentInfo paymentInfo,

    @JsonProperty("rental_station_info")
    RentalStationInfo rentalStationInfo,

    @JsonProperty("return_station_info")
    RentalStationInfo returnStationInfo
) {

  public static RentalRecordData of(RentalRecord rentalRecord) {
    RentalRecordInfo rentalRecordInfo = RentalRecordInfo.create(rentalRecord);
    PaymentInfo paymentInfo = PaymentInfo.create(rentalRecord.getPayment());
    RentalStationInfo rentalStationInfo = RentalStationInfo.create(rentalRecord.getRentalStation());
    RentalStationInfo returnStationInfo = RentalStationInfo.create(rentalRecord.getReturnStation());
    return new RentalRecordData(rentalRecordInfo, paymentInfo, rentalStationInfo, returnStationInfo);
  }

  public record RentalRecordInfo(
      @JsonSerialize(using = LocalDateTimeSerializer.class)
      @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
      @JsonProperty("rental_time")
      LocalDateTime rentalTime,

      @JsonSerialize(using = LocalDateTimeSerializer.class)
      @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
      @JsonProperty("return_time")
      LocalDateTime returnTime,

      @JsonSerialize(using = LocalDateTimeSerializer.class)
      @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
      @JsonProperty("expected_return_time")
      LocalDateTime expectedReturnTime,

      @JsonProperty("rental_status")
      RentalStatus rentalStatus
  ) {

    private static RentalRecordInfo create(RentalRecord rentalRecord) {
      return new RentalRecordInfo(rentalRecord.getRentalTime(),
          rentalRecord.getReturnTime(), rentalRecord.getExpectedReturnTime(), rentalRecord.getRentalStatus());
    }
  }

  public record PaymentInfo(
      @JsonProperty("merchant_uid")
      String merchantUid,

      Integer amount,

      @JsonSerialize(using = LocalDateTimeSerializer.class)
      @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
      @JsonProperty("create_date")
      LocalDateTime createDate,

      @JsonProperty("payment_status")
      PaymentStatus paymentStatus
  ) {

    private static PaymentInfo create(Payment payment) {
      return new PaymentInfo(payment.getMerchantUid(), payment.getAmount(), payment.getCreateDate(),
          payment.getPaymentStatus());
    }
  }

  public record RentalStationInfo(
      @JsonProperty("station_id")
      Long stationId,

      String name
  ) {


    private static RentalStationInfo create(RentalStation rentalStation) {
      return Optional.ofNullable(rentalStation)
                     .map(station -> new RentalStationInfo(station.getId(), station.getName()))
                     .orElse(null);
    }
  }
}
