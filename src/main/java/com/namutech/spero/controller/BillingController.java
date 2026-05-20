package com.namutech.spero.controller;

import com.namutech.spero.common.ApiResponse;
import com.namutech.spero.common.util.PagingUtil;
import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.dto.BillingSearchConditionDTO;
import com.namutech.spero.entity.Billing;
import com.namutech.spero.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        Page<Billing> billings = billingService.getAllBillingSearch(condition);
        Page<BillingDTO> billingDTOS = PagingUtil.map(billings, BillingDTO::of);
        return ResponseEntity.ok(ApiResponse.page(billingDTOS));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllBillings(Authentication authentication,
                                                        @RequestParam(defaultValue = "1") int page,
                                                        @RequestParam(defaultValue = "5") int size) {

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            log.info("sub: {}", jwt.getSubject());
            log.info("email: {}", jwt.getClaimAsString("email"));
            log.info("username: {}", jwt.getClaimAsString("preferred_username"));
        } else {
            log.warn("JWT principal not found or not instance of Jwt");
        }

        Page<BillingDTO> pagedResult = billingService.getPagedBillings(page, size);
        return ResponseEntity.ok(ApiResponse.page(pagedResult));
    }

    @PutMapping("/{billingId}")
    public ResponseEntity<ApiResponse<?>> updateBilling(@PathVariable Long billingId,
                                                        @RequestBody BillingDTO billingDTO) {
        BillingDTO updatedBilling = billingService.updateBilling(billingId, billingDTO);
        return ResponseEntity.ok(ApiResponse.success(updatedBilling));
    }

    @DeleteMapping("/{billingId}")
    public ResponseEntity<ApiResponse<?>> deleteBilling(@PathVariable Long billingId) {
        billingService.deleteBilling(billingId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
