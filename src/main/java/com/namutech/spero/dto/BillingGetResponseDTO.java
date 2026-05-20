package com.namutech.spero.dto;

import com.namutech.spero.entity.Billing;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BillingGetResponseDTO {

    private Long billingId;
    private String cloudId;
    private Double amount;
    private Double amountKRW;
    private Double tax;
    private Double discountAmount;
    private Double useAmount;
    private String billingDate;
    private String cspType;
    private String defaultCurrency;
    private LocalDateTime createAt;

    public static BillingGetResponseDTO of(Billing billing) {
        return BillingGetResponseDTO.builder()
                .billingId(billing.getBillingId())
                .cloudId(billing.getCloudId())
                .amount(billing.getAmount())
                .amountKRW(billing.getAmountKRW())
                .tax(billing.getTax())
                .discountAmount(billing.getDiscountAmount())
                .useAmount(billing.getUseAmount())
                .billingDate(billing.getBillingDate())
                .cspType(billing.getCspType())
                .defaultCurrency(billing.getDefaultCurrency())
                .createAt(billing.getCreateAt())
                .build();
    }

    /**
     * Custom Getter 메서드 (JSON 응답에 포함됨)
     * @return Map
     */
    public Map<String, Object> getBillingSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("displayValue", amount + defaultCurrency);
        return summary;
    }
}
