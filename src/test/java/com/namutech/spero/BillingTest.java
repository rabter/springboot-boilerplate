package com.namutech.spero;

import com.namutech.spero.entity.Billing;
import com.namutech.spero.service.BillingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
public class BillingTest {

    @Autowired
    private BillingService billingService;

    @Test
    public void getAllBillings() {
        List<Billing> billings = billingService.getAllBillings();

        assertEquals(2, billings.size());
    }
}
