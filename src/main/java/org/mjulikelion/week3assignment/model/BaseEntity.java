package org.mjulikelion.week3assignment.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {
    @Id// PK 역할
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")// UUID로 자동 생성되며, uuid2라는 이름의 생성기를 사용한다.
    @Column(updatable = false, unique = true, nullable = false)// 업데이트가 불가능하고, 고유하며, 비어있을 수 없다.
    private UUID id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
