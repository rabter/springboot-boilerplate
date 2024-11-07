package com.namutech.spero.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * HTTP 클라이언트 라이브러리 RestTemplate bean 등록
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5초 연결 타임아웃

        return new RestTemplate(factory);
    }
}
