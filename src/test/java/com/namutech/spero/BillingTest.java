package com.namutech.spero;

import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.dto.BillingSearchConditionDTO;
import com.namutech.spero.entity.Billing;
import com.namutech.spero.repository.BillingRepository;
import com.namutech.spero.service.BillingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@Transactional
@Rollback
//@Commit
public class BillingTest {

    @Autowired
    private BillingService billingService;

    @Autowired
    private BillingRepository billingRepository;

    @Test
    @DisplayName("동적 페이징 조회 테스트")
    public void findAll() {
        BillingSearchConditionDTO condition = BillingSearchConditionDTO.builder()
                .cspType("aws")
                .defaultCurrency("U")
//                .billingDate("202501")
                .pageNumber(1)
                .pageSize(3)
                .build();
        Page<Billing> billings = billingService.getAllBillingSearch(condition);
    }

    @Test
    public void getAllBillings() {
        List<BillingDTO> billings = billingService.getAllBillings();

        log.info("Billing List Size : {}", billings.size());
        assertEquals(11, billings.size());
    }

    @DisplayName("CSP별 Billing 조회")
    @Test
    public void getAllBillingByCspType() {
        String cspType = "aws";
        List<Billing> billings = billingService.getAllBillingByCspType(cspType);

        assertEquals(6, billings.size());
    }

    @Test
    public void createBilling() {
        BillingDTO billingDTO = BillingDTO.builder()
                .billingDate("2024-10")
                .cspType("aws")
                .defaultCurrency("USD")
                .amount(10.0)
                .amountKRW(13000.0)
                .cloudId("cloud005-aws-ap-northeast-2")
                .build();

        Billing billing = billingService.createBilling(billingDTO);

        assertEquals("USD", billing.getDefaultCurrency());
    }

    @Test
    @DisplayName("Billing 수정 테스트")
    public void updateBilling() {
        Long billingId = 1L;
        BillingDTO billingDTO = BillingDTO.builder().defaultCurrency("KRW").build();

        BillingDTO updatedBilling = billingService.updateBilling(billingId, billingDTO);
        assertEquals("KRW", updatedBilling.getDefaultCurrency());
        assertEquals("ktCloud", updatedBilling.getCspType());
    }

    @Test
    @DisplayName("Billing 삭제 테스트")
    public void deleteBilling() {
        Long billingId = 1L;
        billingService.deleteBilling(billingId);

        assertEquals(10, billingRepository.findAll().size());
    }
}
