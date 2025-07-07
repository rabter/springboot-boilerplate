package com.namutech.spero.common.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class RestClient {

    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> ResponseEntity<T> sendRequest(
            String url,
            HttpMethod method,
            HttpHeaders headers,
            Object body,
            Class<T> responseType
    ) {
        try {
            HttpEntity<?> entity = new HttpEntity<>(body, headers);

            log.info("Sending {} request to URL: {}", method, url);
            log.debug("Request Headers: {}", headers);
            log.debug("Request Body: {}", body);

            ResponseEntity<T> response = restTemplate.exchange(url, method, entity, responseType);

            log.info("Received response with status: {}", response.getStatusCode());
            log.debug("Response body: {}", response.getBody());

            return response;

        } catch (HttpStatusCodeException ex) {
            String responseBody = ex.getResponseBodyAsString();
            log.error("API 호출 실패: {} - {}", ex.getStatusCode(), responseBody);
            throw new RuntimeException("API 호출 실패: " + ex.getStatusCode() + " - " + responseBody, ex);

        } catch (Exception ex) {
            log.error("API 호출 중 오류 발생: {}", ex.getMessage(), ex);
            throw new RuntimeException("API 호출 중 오류 발생: " + ex.getMessage(), ex);
        }
    }
}
