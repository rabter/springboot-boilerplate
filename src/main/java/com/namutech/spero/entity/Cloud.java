package com.namutech.spero.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tbCloud")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Cloud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String cloudId;

    private String cloudName;
    private String vendor;

    @CreatedDate
    private LocalDateTime createAt;

    @Builder
    public Cloud(String cloudId, String cloudName, String vendor) {
        this.cloudId = cloudId;
        this.cloudName = cloudName;
        this.vendor = vendor;
    }
}
