package com.namutech.spero.common.client;

import com.namutech.spero.common.client.dto.BillingListResponseDTO;
import com.namutech.spero.dto.BillingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExternalApiClient {

    private final WebClient webClient;

    public Mono<List<BillingDTO>> getBillingData() {
        return webClient.get()
                .uri("/api/billings")
                .retrieve()
                .bodyToMono(BillingListResponseDTO.class)
                .map(BillingListResponseDTO::getData);
    }

    public Mono<String> getBillingDataAsString() {
        return webClient.get()
                .uri("/api/billings")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<List<BillingDTO>> searchBillings(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/billings/search")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToFlux(BillingDTO.class)
                .collectList();
    }
}
