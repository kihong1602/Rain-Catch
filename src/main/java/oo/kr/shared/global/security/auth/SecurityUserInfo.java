package oo.kr.shared.global.security.auth;

import oo.kr.shared.domain.member.User;

public record SecurityUserInfo(
    Long id,
    String nickName,
    String password,
    String email
) {

  public static SecurityUserInfo createOAuth2UserInfo(User user) {
    return new SecurityUserInfo(user.getId(), user.getNickName(), null, user.getEmail());
  }

  public static SecurityUserInfo createFormUserInfo(User user) {
    return new SecurityUserInfo(user.getId(), user.getNickName(), user.getPassword(), user.getEmail());
  }
}