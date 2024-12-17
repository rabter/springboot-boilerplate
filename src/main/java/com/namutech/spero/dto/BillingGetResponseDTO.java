package com.namutech.spero.dto;

import com.namutech.spero.entity.Billing;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
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

    public static <T, R> Page<R> mapPage(Page<T> source, Function<T, R> mapper) {
        List<R> convertList = source.getContent()
                .stream()
                .map(mapper)
                .toList();

        return new PageImpl<>(convertList, source.getPageable(), source.getTotalElements());
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
