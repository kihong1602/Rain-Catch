package oo.kr.shared.domain.user;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ProviderType {
  GOOGLE, KAKAO, LOCAL;

  private static final Map<String, ProviderType> PROVIDER_TYPES =
      Collections.unmodifiableMap(Arrays.stream(values())
                                        .collect(Collectors.toMap(ProviderType::name, Function.identity())));

  public static ProviderType find(String providerType) {
    return Optional.ofNullable(PROVIDER_TYPES.get(providerType))
                   .orElse(LOCAL);
  }

}
