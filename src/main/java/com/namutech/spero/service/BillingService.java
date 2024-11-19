package com.namutech.spero.service;

import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.entity.Billing;
import com.namutech.spero.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
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
            System.out.println("예외발생 :" + e.getMessage());
            throw e;
        }
    }
}
