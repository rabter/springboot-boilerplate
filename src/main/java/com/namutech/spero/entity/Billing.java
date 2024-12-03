package com.namutech.spero.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "tbBilling")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingId;

    private Double amount;
    private Double amountKRW;
    private Double tax;
    private Double discountAmount;
    private Double useAmount;
    private String billingDate;
    private String cspType;
    private String defaultCurrency;

    private String cloudId;

    @CreatedDate
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "billing", fetch = FetchType.LAZY)
    private List<BillingDetail> billingDetails;

    @Builder
    public Billing(Double amount, Double amountKRW, String billingDate, String cspType, String defaultCurrency, String cloudId) {
        this.amount = amount;
        this.amountKRW = amountKRW;
        this.billingDate = billingDate;
        this.cspType = cspType;
        this.defaultCurrency = defaultCurrency;
        this.cloudId = cloudId;
    }
}


