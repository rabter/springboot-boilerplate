package com.namutech.spero.service;

import com.namutech.spero.common.service.GenericService;
import com.namutech.spero.common.util.PredicateBuilderHelper;
import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.dto.BillingSearchConditionDTO;
import com.namutech.spero.entity.Billing;
import com.namutech.spero.entity.QBilling;
import com.namutech.spero.repository.BillingRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BillingService extends GenericService<Billing, QBilling, BillingSearchConditionDTO> {

    private final BillingRepository billingRepository;


    public Page<Billing> getAllBillingSearch(BillingSearchConditionDTO condition) {
        return findAll(condition, QBilling.billing, q -> buildPredicate(condition));
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

    @Transactional
    public BillingDTO deleteBilling(Long billingId) {
        Billing billing = billingRepository.findById(billingId)
                .orElseThrow(() -> new IllegalArgumentException("Billing not found with id: " + billingId));
        billingRepository.delete(billing);
        return BillingDTO.of(billing);
    }

    @Override
    protected PathBuilder<Billing> getPathBuilder() {
        return new PathBuilder<>(Billing.class, "billing");
    }

    @Override
    protected BooleanBuilder buildPredicate(BillingSearchConditionDTO condition) {
        PathBuilder<Billing> path = getPathBuilder();
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(PredicateBuilderHelper.eq(path, "cspType", condition.getCspType()));
        builder.and(PredicateBuilderHelper.like(path, "defaultCurrency", condition.getDefaultCurrency()));
        return builder;
    }
}
