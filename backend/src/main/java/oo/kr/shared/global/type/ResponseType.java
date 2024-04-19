package oo.kr.shared.global.type;

public enum ResponseType {

  SUCCESS, FAIL;

  public static ResponseType of(boolean isSuccess) {
    return isSuccess ? SUCCESS : FAIL;
  }

}