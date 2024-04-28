package oo.kr.shared.domain.rentalstation.controller.request;

public record EditStationInfo(
    String name,
    String address,
    Double latitude,
    Double longitude
) {

}
