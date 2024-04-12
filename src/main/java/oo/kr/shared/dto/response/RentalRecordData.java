package oo.kr.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.Objects;
import oo.kr.shared.domain.payment.Payment;
import oo.kr.shared.domain.payment.PaymentStatus;
import oo.kr.shared.domain.rentalrecord.RentalRecord;
import oo.kr.shared.domain.rentalrecord.RentalStatus;
import oo.kr.shared.domain.rentalstation.RentalStation;

public class RentalRecordData {

  @JsonProperty("rental_record_info")
  private RentalRecordInfo rentalRecordInfo;

  @JsonProperty("payment_info")
  private PaymentInfo paymentInfo;

  @JsonProperty("rental_station_info")
  private RentalStationInfo rentalStationInfo;

  @JsonProperty("return_station_info")
  private RentalStationInfo returnStationInfo;


  private RentalRecordData(RentalRecordInfo rentalRecordInfo, PaymentInfo paymentInfo,
      RentalStationInfo rentalStationInfo,
      RentalStationInfo returnStationInfo) {
    this.rentalRecordInfo = rentalRecordInfo;
    this.paymentInfo = paymentInfo;
    this.rentalStationInfo = rentalStationInfo;
    this.returnStationInfo = returnStationInfo;
  }

  public static RentalRecordData of(RentalRecord rentalRecord) {
    Payment payment = rentalRecord.getPayment();
    RentalRecordInfo rentalRecordInfo = new RentalRecordInfo(rentalRecord.getRentalTime(),
        rentalRecord.getReturnTime(), rentalRecord.getExpectedReturnTime(), rentalRecord.getRentalStatus());
    PaymentInfo paymentInfo = new PaymentInfo(payment.getMerchantUid(), payment.getAmount(), payment.getCreateDate(),
        payment.getPaymentStatus());
    RentalStation rentalStation = rentalRecord.getRentalStation();
    RentalStationInfo rentalStationInfo = new RentalStationInfo(rentalStation.getId(), rentalStation.getName());
    RentalStationInfo returnStationInfo = null;
    RentalStation returnStation = rentalRecord.getReturnStation();
    if (Objects.nonNull(returnStation)) {
      returnStationInfo = new RentalStationInfo(returnStation.getId(), returnStation.getName());
    }
    return new RentalRecordData(rentalRecordInfo, paymentInfo, rentalStationInfo, returnStationInfo);
  }

  public RentalRecordInfo getRentalRecordInfo() {
    return rentalRecordInfo;
  }

  public PaymentInfo getPaymentInfo() {
    return paymentInfo;
  }

  public RentalStationInfo getRentalStationInfo() {
    return rentalStationInfo;
  }

  public RentalStationInfo getReturnStationInfo() {
    return returnStationInfo;
  }

  public static class RentalRecordInfo {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("rental_time")
    private LocalDateTime rentalTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("return_time")
    private LocalDateTime returnTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("expected_return_time")
    private LocalDateTime expectedReturnTime;

    @JsonProperty("rental_status")
    private RentalStatus rentalStatus;

    public RentalRecordInfo(LocalDateTime rentalTime, LocalDateTime returnTime, LocalDateTime expectedReturnTime,
        RentalStatus rentalStatus) {
      this.rentalTime = rentalTime;
      this.returnTime = returnTime;
      this.expectedReturnTime = expectedReturnTime;
      this.rentalStatus = rentalStatus;
    }

    public LocalDateTime getRentalTime() {
      return rentalTime;
    }

    public LocalDateTime getReturnTime() {
      return returnTime;
    }

    public LocalDateTime getExpectedReturnTime() {
      return expectedReturnTime;
    }

    public RentalStatus getRentalStatus() {
      return rentalStatus;
    }
  }

  public static class PaymentInfo {

    @JsonProperty("merchant_uid")
    private String merchantUid;

    private Integer amount;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("create_date")
    private LocalDateTime createDate;

    @JsonProperty("payment_status")
    private PaymentStatus paymentStatus;

    public PaymentInfo(String merchantUid, Integer amount, LocalDateTime createDate, PaymentStatus paymentStatus) {
      this.merchantUid = merchantUid;
      this.amount = amount;
      this.createDate = createDate;
      this.paymentStatus = paymentStatus;
    }

    public String getMerchantUid() {
      return merchantUid;
    }

    public Integer getAmount() {
      return amount;
    }

    public LocalDateTime getCreateDate() {
      return createDate;
    }

    public PaymentStatus getPaymentStatus() {
      return paymentStatus;
    }
  }

  public static class RentalStationInfo {

    @JsonProperty("station_id")
    private Long stationId;

    private String name;

    public RentalStationInfo(Long stationId, String name) {
      this.stationId = stationId;
      this.name = name;
    }

    public Long getStationId() {
      return stationId;
    }

    public String getName() {
      return name;
    }
  }
}
