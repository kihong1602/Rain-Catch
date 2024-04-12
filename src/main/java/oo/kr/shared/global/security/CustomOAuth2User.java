package oo.kr.shared.global.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import oo.kr.shared.domain.member.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {

  private Long id;

  private String name;

  private String email;

  private Collection<? extends GrantedAuthority> authorities;

  private Map<String, Object> attributes;

  public static CustomOAuth2User create(Member member, Map<String, Object> attributes) {
    Long id = member.getId();
    String name = member.getName();
    String email = member.getEmail();
    String roleKey = member.getRoleKey();
    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(roleKey));
    return new CustomOAuth2User(id, name, email, authorities, attributes);
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getName() {
    return name;
  }

}
