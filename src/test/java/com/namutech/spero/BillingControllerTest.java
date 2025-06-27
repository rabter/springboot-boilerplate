package com.namutech.spero;

import com.namutech.spero.controller.BillingController;
import com.namutech.spero.entity.Billing;
import com.namutech.spero.service.BillingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(BillingController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class BillingControllerTest {

    @MockBean
    private BillingService billingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("[GET] /api/billings ")
    public void getAllBillingsTest() throws Exception {

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/billings"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("[GET] /api/billings/search ")
    public void getAllBillingSearchTest() throws Exception {
        // given
        String requestBody = """
            {
                "defaultCurrency": "KRW",
                "cspType": "ktCloud",
                "billingDate": "202407",
                "pageNumber": 1,
                "pageSize": 4
            }
        """;

        // Mock the service response
        when(billingService.getAllBillingSearch(any())).thenReturn(new PageImpl<>(List.of(Billing.builder().build())));

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/billings/search")
                        .contentType("application/json")
                        .content(requestBody));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/billings/search")
                        .contentType("application/json")
                        .content(requestBody));

    }
}
