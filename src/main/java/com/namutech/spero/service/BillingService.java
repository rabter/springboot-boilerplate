package com.namutech.spero.service;

import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.entity.Billing;
import com.namutech.spero.repository.BillingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    public Page<BillingDTO> getPagedBillings(int page, int size) {
        return billingRepository.findAllByOrderByBillingIdDesc(PageRequest.of(page, size))
                .map(BillingDTO::of);
    }

    public List<BillingDTO> getAllBillings() {
        List<Billing> billings = billingRepository.findAll();
        return billings.stream()
                .map(BillingDTO::of)
                .collect(Collectors.toList());
    }

    public List<Billing> getAllBillingByCspType(String cspType) {
        return billingRepository.findAllByCspType(cspType);
    }

    @Transactional
    public Billing createBilling(BillingDTO billingDTO) {
        try {
            Billing billing = billingDTO.toEntity();
            return billingRepository.save(billing);
        } catch (IllegalArgumentException e) {
            log.info("예외발생 : {}", e.getMessage());
            throw e;
        }
    }
}
