package com.expensetracker.main.domain.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(name = "created_at")
    // @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    // protected LocalDateTime createdAt;
    protected long createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    // @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    // protected LocalDateTime updatedAt;
    protected long updatedAt;

    public BaseEntity(Long id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now().getEpochSecond();
        updatedAt = Instant.now().getEpochSecond();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now().getEpochSecond();
    }
}