package com.namutech.spero.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *  외부 API 통신을 통한 비즈니스 로직를 담당하는 Service 레이어 클래스
 */
@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;

    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getData(String url) {
        return restTemplate.getForEntity(url, String.class);
    }
}
