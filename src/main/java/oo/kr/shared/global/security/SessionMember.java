package oo.kr.shared.global.security;

import oo.kr.shared.domain.member.Member;

public class SessionMember {

  private Long id;
  private String email;
  private String nickName;
  private String image;

  public SessionMember(Member member) {
    this.id = member.getId();
    this.email = member.getEmail();
    this.nickName = member.getName();
    this.image = member.getImage();
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getNickName() {
    return nickName;
  }

  public String getImage() {
    return image;
  }
}
