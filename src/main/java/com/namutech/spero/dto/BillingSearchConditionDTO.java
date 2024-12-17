package com.namutech.spero.dto;

import com.namutech.spero.common.dto.BaseSearchDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class BillingSearchConditionDTO extends BaseSearchDTO {
    private String cspType;
    private String billingDate;
    private String defaultCurrency;
}
