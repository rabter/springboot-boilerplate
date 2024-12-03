package com.namutech.spero.controller;

import com.namutech.spero.common.ApiResponse;
import com.namutech.spero.common.dto.PagingInfoDTO;
import com.namutech.spero.common.util.PagingUtil;
import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/billings")
public class BillingController {

    private final BillingService billingService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllBillings(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size) {

        Page<BillingDTO> pagedResult = billingService.getPagedBillings(page, size);

        PagingInfoDTO pagingInfo = PagingUtil.createPagingInfo(pagedResult);
        return ResponseEntity.ok(new ApiResponse<>(true, pagedResult.getContent(), pagingInfo));
    }

}
