package oo.kr.shared.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.global.type.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends BaseEntity {

  @Column(nullable = false)
  private String nickName;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  private String image;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProviderType providerType = ProviderType.LOCAL;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role = Role.USER;

  public User(String email, String password) {
    this.nickName = email;
    this.email = email;
    this.password = password;
  }

  public User(String nickName, String email, String image, String providerType) {
    this.nickName = nickName;
    this.email = email;
    this.image = image;
    this.providerType = ProviderType.find(providerType);
  }

  public String getRoleKey() {
    return role.getKey();
  }

}