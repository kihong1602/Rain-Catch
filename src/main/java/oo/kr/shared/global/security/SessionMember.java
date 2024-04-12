package oo.kr.shared.global.security;

import oo.kr.shared.domain.member.Member;

public record SessionMember(
    Long id,
    String email,
    String nickName,
    String image
) {

  public static SessionMember create(Member member) {
    return new SessionMember(member.getId(), member.getEmail(), member.getName(), member.getImage());
  }

}