package oo.kr.shared.global.security.auth;

import oo.kr.shared.domain.user.ProviderType;
import oo.kr.shared.domain.user.User;

public record SecurityUserInfo(
    Long id,
    String nickName,
    String password,
    String email,
    ProviderType providerType
) {

  public static SecurityUserInfo createOAuth2UserInfo(User user) {
    return new SecurityUserInfo(user.getId(), user.getNickName(), null, user.getEmail(), user.getProviderType());
  }

  public static SecurityUserInfo createFormUserInfo(User user) {
    return new SecurityUserInfo(user.getId(), user.getNickName(), user.getPassword(), user.getEmail(),
        user.getProviderType());
  }
}