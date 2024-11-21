package com.namutech.spero.repository;

import com.namutech.spero.entity.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findAllByCspType(String cspType);

    Page<Billing> findAllByOrderByBillingIdDesc(Pageable pageable);
}
