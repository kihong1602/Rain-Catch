package oo.kr.shared.global.security.auth;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.user.domain.User;
import oo.kr.shared.domain.user.domain.repository.UserRepository;
import oo.kr.shared.global.security.auth.OAuth2ProviderRegistry.OAuth2ProviderType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserLoadProcessor = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = oauth2UserLoadProcessor.loadUser(oAuth2UserRequest);
    String registrationId = oAuth2UserRequest.getClientRegistration()
                                             .getRegistrationId();
    OAuth2ProviderType providerType = OAuth2ProviderRegistry.getType(registrationId);
    String userNameAttributeName = oAuth2UserRequest.getClientRegistration()
                                                    .getProviderDetails()
                                                    .getUserInfoEndpoint()
                                                    .getUserNameAttributeName();
    Map<String, Object> oAuth2UserResponse = oAuth2User.getAttributes();
    OAuth2UserAttributes oAuth2UserAttributes = OAuth2UserAttributes.of(providerType, userNameAttributeName,
        oAuth2UserResponse);

    User user = save(oAuth2UserAttributes);
    return PrincipalDetails.create(user, oAuth2UserResponse);
  }

  private User save(OAuth2UserAttributes oAuth2UserAttributes) {
    User user = userRepository.findByEmail(oAuth2UserAttributes.email())
                              .orElse(oAuth2UserAttributes.toUser());
    user.addOAuth2UserPassword(bCryptPasswordEncoder.encode(user.getProviderType() + "_" + user.getEmail()));
    return userRepository.save(user);
  }

}