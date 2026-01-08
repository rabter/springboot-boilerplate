package com.namutech.spero.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbServiceObservedK8sResource")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ServiceObservedK8sResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long observedK8sResourceId;

    @Column(nullable = false)
    private Long serviceId;

    @Column(nullable = false, length = 50)
    private String cloudId;

    @Column(length = 100)
    private String namespace;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 50)
    private String kind;

    @Column(length = 100)
    private String resourceUid;

    @Column(length = 30)
    private String status;

    @Column(length = 1)
    private String hasParentYn;

    private Long parentObservedK8sResourceId;

    @Column(length = 100)
    private String parentResourceUid;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String detailYaml;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    // (선택) insert/update 타임스탬프 자동화
    @PrePersist
    public void prePersist() {
        if (createAt == null) createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateAt = LocalDateTime.now();
    }
}
