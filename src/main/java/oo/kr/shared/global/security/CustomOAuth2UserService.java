package oo.kr.shared.global.security;

import java.util.Map;
import javax.servlet.http.HttpSession;
import oo.kr.shared.domain.member.Member;
import oo.kr.shared.domain.member.MemberRepository;
import oo.kr.shared.global.security.OAuth2ProviderRegistry.OAuth2ProviderType;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final MemberRepository memberRepository;
  private final HttpSession httpSession;

  public CustomOAuth2UserService(MemberRepository memberRepository, HttpSession httpSession) {
    this.memberRepository = memberRepository;
    this.httpSession = httpSession;
  }

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

    Member member = save(oAuth2UserAttributes);
    httpSession.setAttribute("user", new SessionMember(member));
    return CustomOAuth2User.create(member, oAuth2UserResponse);
  }

  private Member save(OAuth2UserAttributes oAuth2UserAttributes) {
    Member member = memberRepository.findByEmail(oAuth2UserAttributes.getEmail())
                                    .orElse(oAuth2UserAttributes.toMember());
    return memberRepository.save(member);
  }

}