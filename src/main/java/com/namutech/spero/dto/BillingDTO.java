package com.namutech.spero.dto;

import com.namutech.spero.entity.Billing;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BillingDTO {

    private String billingDate;
    private String cspType;
    private String defaultCurrency;

    private Double amount;
    private Double amountKRW;

    private String cloudId;

    @Builder
    public BillingDTO(String billingDate, String cspType, String defaultCurrency, Double amount, Double amountKRW, String cloudId) {
        this.billingDate = billingDate;
        this.cspType = cspType;
        this.defaultCurrency = defaultCurrency;
        this.amount = amount;
        this.amountKRW = amountKRW;
        this.cloudId = cloudId;
    }

    public Billing toEntity() {
        return Billing.builder()
                .billingDate(billingDate)
                .cspType(cspType)
                .defaultCurrency(defaultCurrency)
                .amount(amount)
                .amountKRW(amountKRW)
                .cloudId(cloudId)
                .build();
    }
}
