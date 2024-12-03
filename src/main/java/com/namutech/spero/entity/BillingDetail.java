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
@Table(name = "tbBillingDetail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BillingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingDetailId;

    private String resourceType;
    private String resourceInfo;
    private Double resourceUseAmount;
    private Double resourceUseAmountKRW;
    private String billingDate;
    private String cspType;

    @CreatedDate
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billingId")
    private Billing billing;

    @Builder
    public BillingDetail(String resourceType, String resourceInfo, Double resourceUseAmount, Double resourceUseAmountKRW, String billingDate, String cspType) {
        this.resourceType = resourceType;
        this.resourceInfo = resourceInfo;
        this.resourceUseAmount = resourceUseAmount;
        this.resourceUseAmountKRW = resourceUseAmountKRW;
        this.billingDate = billingDate;
        this.cspType = cspType;
    }
}
