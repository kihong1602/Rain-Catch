package oo.kr.shared.global.security.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import oo.kr.shared.domain.member.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PrincipalDetails implements OAuth2User, UserDetails {

  private SecurityUserInfo securityUserInfo;

  private Collection<? extends GrantedAuthority> authorities;

  private Map<String, Object> attributes;

  private PrincipalDetails(SecurityUserInfo securityUserInfo, Collection<? extends GrantedAuthority> authorities) {
    this.securityUserInfo = securityUserInfo;
    this.authorities = authorities;
  }

  public static PrincipalDetails create(User user, Map<String, Object> attributes) {
    String roleKey = user.getRoleKey();
    SecurityUserInfo securityUserInfo = SecurityUserInfo.createOAuth2UserInfo(user);
    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(roleKey));
    return new PrincipalDetails(securityUserInfo, authorities, attributes);
  }

  public static PrincipalDetails create(User user) {
    String roleKey = user.getRoleKey();
    SecurityUserInfo userInfo = SecurityUserInfo.createFormUserInfo(user);
    Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(roleKey));
    return new PrincipalDetails(userInfo, authorities);
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
  public String getPassword() {
    return securityUserInfo.password();
  }

  @Override
  public String getUsername() {
    return securityUserInfo.email();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getName() {
    return securityUserInfo.email();
  }

}
