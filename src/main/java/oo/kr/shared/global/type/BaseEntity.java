package oo.kr.shared.global.type;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
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
