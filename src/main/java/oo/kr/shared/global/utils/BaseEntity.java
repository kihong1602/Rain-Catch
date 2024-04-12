package oo.kr.shared.global.utils;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column(name = "create_date", updatable = false, columnDefinition = "datetime default now()")
  private LocalDateTime createDate;

  @LastModifiedDate
  @Column(name = "update_date", columnDefinition = "datetime default now()")
  private LocalDateTime updateDate;

  protected BaseEntity() {
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public LocalDateTime getUpdateDate() {
    return updateDate;
  }
}
