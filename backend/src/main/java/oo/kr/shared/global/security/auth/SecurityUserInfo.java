package oo.kr.shared.global.security.auth;

import oo.kr.shared.domain.user.domain.ProviderType;
import oo.kr.shared.domain.user.domain.User;

public record SecurityUserInfo(
    String nickName,
    String password,
    String email,
    ProviderType providerType
) {

  public static SecurityUserInfo createOAuth2UserInfo(User user) {
    return new SecurityUserInfo(user.getNickName(), null, user.getEmail(), user.getProviderType());
  }

  public static SecurityUserInfo createFormUserInfo(User user) {
    return new SecurityUserInfo(user.getNickName(), user.getPassword(), user.getEmail(), user.getProviderType());
  }
}