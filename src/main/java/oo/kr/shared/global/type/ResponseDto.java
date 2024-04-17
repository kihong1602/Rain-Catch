package oo.kr.shared.global.type;

import lombok.Getter;

@Getter
public abstract class ResponseDto {

  private ResponseType result;

  protected ResponseDto(ResponseType result) {
    this.result = result;
  }

}