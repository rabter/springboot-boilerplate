package com.namutech.spero.controller;

import com.namutech.spero.common.ApiResponse;
import com.namutech.spero.common.dto.PagingInfoDTO;
import com.namutech.spero.common.util.PagingUtil;
import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.dto.BillingGetResponseDTO;
import com.namutech.spero.dto.BillingSearchConditionDTO;
import com.namutech.spero.entity.Billing;
import com.namutech.spero.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/billings")
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<?>> getAllBillingSearch(@RequestBody BillingSearchConditionDTO condition) {
        // 1. Entity 조회
        Page<Billing> billings = billingService.getAllBillingSearch(condition);

        // 2. Entity to DTO 변환
        Page<BillingDTO> billingDTOS = BillingGetResponseDTO.mapPage(billings, BillingDTO::of);

        // 3. PagingInfo 생성
        PagingInfoDTO pagingInfoDTO = PagingUtil.buildPagingInfo(billingDTOS);

        return ResponseEntity.ok(new ApiResponse<>(true, billingDTOS.getContent(), pagingInfoDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllBillings(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size) {

        Page<BillingDTO> pagedResult = billingService.getPagedBillings(page, size);

        PagingInfoDTO pagingInfo = PagingUtil.buildPagingInfo(pagedResult);
        return ResponseEntity.ok(new ApiResponse<>(true, pagedResult.getContent(), pagingInfo));
    }

    @PutMapping("/{billingId}")
    public ResponseEntity<ApiResponse<?>> updateBilling(@PathVariable Long billingId, @RequestBody BillingDTO billingDTO) {
        BillingDTO updatedBilling = billingService.updateBilling(billingId, billingDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, updatedBilling, null));
    }
}
