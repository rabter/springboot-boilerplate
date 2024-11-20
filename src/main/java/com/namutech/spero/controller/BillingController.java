package com.namutech.spero.controller;

import com.namutech.spero.common.ApiResponse;
import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/billings")
public class BillingController {

    private final BillingService billingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BillingDTO>>> getAllBillings() {
        List<BillingDTO> billings = billingService.getAllBillings();
        return ResponseEntity.ok(new ApiResponse<>(true, billings));
    }
}
