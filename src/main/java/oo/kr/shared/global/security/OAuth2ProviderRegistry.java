package oo.kr.shared.global.security;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class OAuth2ProviderRegistry {

  private static final Set<OAuth2ProviderType> oAuth2ProviderTypes = new HashSet<>();

  static {
    oAuth2ProviderTypes.addAll(List.of(OAuth2ProviderType.values()));
  }

  public static OAuth2ProviderType getType(String registrationId) {
    return oAuth2ProviderTypes.stream()
                              .filter(type -> type.isEqualTo(registrationId))
                              .findAny()
                              .orElseThrow();
  }

  public enum OAuth2ProviderType {
    GOOGLE("google", null, "name", "email", "picture"),
    KAKAO("kakao", "kakao_account", "nickname", "email", "profile_image_url");

    public final String REGISTRATION_ID;
    public final String ATTRIBUTES_FIELD;
    public final String NAME_FIELD;
    public final String EMAIL_FIELD;
    public final String IMAGE_FIELD;

    OAuth2ProviderType(String REGISTRATION_ID, String ATTRIBUTES_FIELD, String NAME_FIELD, String EMAIL_FIELD,
        String IMAGE_FIELD) {
      this.REGISTRATION_ID = REGISTRATION_ID;
      this.ATTRIBUTES_FIELD = ATTRIBUTES_FIELD;
      this.NAME_FIELD = NAME_FIELD;
      this.EMAIL_FIELD = EMAIL_FIELD;
      this.IMAGE_FIELD = IMAGE_FIELD;
    }

    private boolean isEqualTo(String registrationId) {
      return REGISTRATION_ID.equals(registrationId);
    }

    public boolean hasAttributeField() {
      return Objects.nonNull(ATTRIBUTES_FIELD);
    }
  }
}
