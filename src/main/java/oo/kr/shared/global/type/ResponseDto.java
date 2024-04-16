package oo.kr.shared.global.type;

public abstract class ResponseDto {

  private ResponseType result;

  protected ResponseDto(ResponseType result) {
    this.result = result;
  }

}