package oo.kr.shared.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.global.utils.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member extends BaseEntity {

  private String name;
  private String email;
  private String image;
  private String providerType;
  @Enumerated(EnumType.STRING)
  private Role role = Role.USER;

  public Member(String name, String email, String image, String providerType) {
    this.name = name;
    this.email = email;
    this.image = image;
    this.providerType = providerType;
  }

  public String getRoleKey() {
    return role.getKey();
  }

}