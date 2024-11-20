package com.namutech.spero;

import com.namutech.spero.dto.BillingDTO;
import com.namutech.spero.entity.Billing;
import com.namutech.spero.service.BillingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@Transactional
@Rollback
public class BillingTest {

    @Autowired
    private BillingService billingService;

    @Test
    public void getAllBillings() {
        List<BillingDTO> billings = billingService.getAllBillings();

        log.info("Billing List Size : {}", billings.size());
        assertEquals(2, billings.size());
    }

    @Test
    public void getAllBillingByCspType() {
        String cspType = "aws";
        List<Billing> billings = billingService.getAllBillingByCspType(cspType);

        assertEquals(1, billings.size());
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
}
