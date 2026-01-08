package com.namutech.spero.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbServiceDesiredK8sResource")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)

public class ServiceDesiredK8sResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long desiredK8sResourceId;

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

    @Column(nullable = false)
    private Integer revision;

    @Lob
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String desiredYaml;

    @Column(nullable = false, length = 1)
    private String appliedYn;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @PrePersist
    public void prePersist() {
        if (createAt == null) createAt = LocalDateTime.now();
        if (revision == null) revision = 1;
        if (appliedYn == null) appliedYn = "N";
    }

    @PreUpdate
    public void preUpdate() {
        updateAt = LocalDateTime.now();
    }

    @Builder
    public ServiceDesiredK8sResource(Long serviceId, String cloudId, String namespace, String kind, String name, int revision, String desiredYaml, String appliedYn) {
        this.serviceId = serviceId;
        this.cloudId = cloudId;
        this.namespace = namespace;
        this.kind = kind;
        this.name = name;
        this.revision = revision;
        this.desiredYaml = desiredYaml;
        this.appliedYn = appliedYn;
    }
}
