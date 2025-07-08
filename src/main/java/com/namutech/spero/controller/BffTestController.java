package com.namutech.spero.controller;

import com.namutech.spero.common.ApiResponse;
import com.namutech.spero.common.client.AsyncExternalApiClient;
import com.namutech.spero.common.client.SyncExternalApiClient;
import com.namutech.spero.common.client.dto.DashboardDTO;
import com.namutech.spero.dto.BillingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bff/proxy")
public class BffTestController {

    private final AsyncExternalApiClient asyncExternalApiClient;
    private final SyncExternalApiClient syncExternalApiClient;

    /**
     * BFF (Backend for Frontend) 테스트용 API (비동기 방식)
     * 외부 API를 호출하여 데이터를 가져오는 예시
     * 이 API는 서브시스템의 결과를 그대로 프록시하여 반환합니다.
     * @return ResponseEntity<ApiResponse<?>> 응답 객체
     */
    @GetMapping("/async")
    public Mono<ResponseEntity<String>> getBillingDataAsync() {
        return asyncExternalApiClient.getBillingDataAsString()
                .map(json -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .body(json));
    }

    /**
     * BFF (Backend for Frontend) 테스트용 API (동기 방식)
     * 외부 API를 호출하여 데이터를 가져오는 예시
     * 이 API는 서브시스템의 결과를 그대로 프록시하여 반환합니다.
     * @return ResponseEntity<ApiResponse<?>> 응답 객체
     */
    @GetMapping("/sync")
    public ApiResponse<List<BillingDTO>> getBillingDataSync() {
       return syncExternalApiClient.getBillingData();
    }

    /**
     * BFF (Backend for Frontend) 테스트용 API
     * 이 API는 외부 API를 호출하여 데이터를 가져오는 예시입니다.
     * 이 API는 여러 서브시스템의 결과를 통합하여 반환합니다.
     * @return ResponseEntity<ApiResponse<?>> 응답 객체
     */
    @GetMapping("/aggregate")
    public Mono<DashboardDTO> getDashboardData() {
        Mono<List<BillingDTO>> billingDataMono = asyncExternalApiClient.getBillingData();

        return Mono.zip(billingDataMono, billingDataMono)
                .map(tuple -> {
                    Integer totalCount = tuple.getT1().size(); // 예시로 총 개수를 계산
                    Integer totalCount2 = tuple.getT2().size(); // 예시로 두 번째 데이터의 총 개수 계산

                    // DashboardDTO는 필요한 데이터를 통합하여 생성합니다.
                    return new DashboardDTO(totalCount, totalCount2);
                });
    }
}
