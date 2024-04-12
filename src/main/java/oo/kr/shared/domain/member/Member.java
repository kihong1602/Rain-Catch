package oo.kr.shared.domain.member;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import oo.kr.shared.global.utils.BaseEntity;

@Entity
public class Member extends BaseEntity {

  private String name;
  private String email;
  private String image;
  private String providerType;
  @Enumerated(EnumType.STRING)
  private Role role = Role.USER;

  protected Member() {
  }

  public Member(String name, String email, String image, String providerType) {
    this.name = name;
    this.email = email;
    this.image = image;
    this.providerType = providerType;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getImage() {
    return image;
  }

  public String getProviderType() {
    return providerType;
  }

  public Role getRole() {
    return role;
  }

  public String getRoleKey() {
    return role.getKey();
  }

}