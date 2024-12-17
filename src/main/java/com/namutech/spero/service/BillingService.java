package com.namutech.spero.service;

import com.namutech.spero.common.service.GenericService;
import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.dto.BillingSearchConditionDTO;
import com.namutech.spero.entity.Billing;
import com.namutech.spero.entity.QBilling;
import com.namutech.spero.repository.BillingRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BillingService extends GenericService<Billing, QBilling, BillingSearchConditionDTO> {

    @Autowired
    private BillingRepository billingRepository;

    private BooleanExpression buildPredicate(QBilling qBilling, BillingSearchConditionDTO condition) {
        BooleanExpression predicate = Expressions.TRUE;

        if(Objects.nonNull(condition.getCspType())) {
            predicate = addCondition(predicate, qBilling.cspType.eq(condition.getCspType()));
        }
        if(Objects.nonNull(condition.getBillingDate())) {
            predicate = addCondition(predicate, qBilling.billingDate.eq(condition.getBillingDate()));
        }
        if(Objects.nonNull(condition.getDefaultCurrency())) {
            predicate = addCondition(predicate, qBilling.defaultCurrency.eq(condition.getDefaultCurrency()));
        }

        return predicate;
    }
    public Page<Billing> findAll(BillingSearchConditionDTO condition) {
        Pageable pageable = PageRequest.of(condition.getPageNumber(), condition.getPageSize());
        return super.findAll(condition, pageable, QBilling.billing, q -> buildPredicate(q, condition));
    }
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

    @Transactional
    public BillingDTO updateBilling(Long billingId, BillingDTO billingDTO) {
        Billing existingBilling = billingRepository.findById(billingId)
                .orElseThrow(() -> new IllegalArgumentException("Billing not found with id: " + billingId));
        existingBilling.updateDefaultCurrency(billingDTO.getDefaultCurrency());
        return BillingDTO.of(existingBilling);
    }
}
