package com.namutech.spero.dto;

import com.namutech.spero.common.dto.PagingInfoDTO;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingGetRequestDTO {
    private BillingDTO billingDTO;
    private PagingInfoDTO pagingInfoDTO;
}
