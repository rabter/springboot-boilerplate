package com.namutech.spero.dto;

import com.namutech.spero.entity.Billing;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class BillingDTO {

    private Long billingId;
    private String billingDate;
    private String cspType;
    private String defaultCurrency;

    private Double amount;
    private Double amountKRW;

    private String cloudId;
    private String status;

    @Builder
    public BillingDTO(Long billingId, String billingDate, String cspType, String defaultCurrency, Double amount, Double amountKRW, String cloudId, String status) {
        this.billingId = billingId;
        this.billingDate = billingDate;
        this.cspType = cspType;
        this.defaultCurrency = defaultCurrency;
        this.amount = amount;
        this.amountKRW = amountKRW;
        this.cloudId = cloudId;
        this.status = status;
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

    public static BillingDTO of(Billing billing) {
        return BillingDTO.builder()
                .billingDate(billing.getBillingDate())
                .cspType(billing.getCspType())
                .defaultCurrency(billing.getDefaultCurrency())
                .amount(billing.getAmount())
                .amountKRW(billing.getAmountKRW())
                .cloudId(billing.getCloudId())
                .build();
    }

    public Map<String, Object> getNonNullFields() {
        Map<String, Object> nonNullFields = new HashMap<>();
        if (cspType != null) nonNullFields.put("cspType", cspType);
        if (billingDate != null) nonNullFields.put("billingDate", billingDate);
        if (defaultCurrency != null) nonNullFields.put("defaultCurrency", defaultCurrency);
        if (amount != null) nonNullFields.put("amount", amount);
        if (amountKRW != null) nonNullFields.put("amountKRW", amountKRW);
        if (cloudId != null) nonNullFields.put("cloudId", cloudId);
        return nonNullFields;
    }
}
