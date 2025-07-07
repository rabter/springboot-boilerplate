package com.namutech.spero.common.client;

import com.namutech.spero.common.ApiResponse;
import com.namutech.spero.dto.BillingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SyncExternalApiClient {

    private final RestClient restClient;

    public ApiResponse<List<BillingDTO>> getBillingData() {
        return restClient.get()
                .uri("/api/billings")
                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<List<BillingDTO>>>() {});
    }
}
