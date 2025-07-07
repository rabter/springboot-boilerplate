package com.namutech.spero;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Slf4j
@SpringBootTest
public class ResponseTypeTest {

    @Autowired
    private RestClient restClient;

    private MockWebServer mockWebServer;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        log.info("MockWebServer started at {}", mockWebServer.url("/"));
    }

    @AfterEach
    void shutdown() throws IOException {
        if (mockWebServer != null) {
            mockWebServer.shutdown();
            log.info("MockWebServer shut down");
        }
    }

    @Test
    void jsonStringTest() throws Exception {
        // 더미 JSON 응답 정의
        String dummyJSON = """
                    {
                        "message": "Hello, World!",
                        "status": "success"
                    }
                """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(dummyJSON)
                .setHeader("Content-Type", "application/json"));

        String baseUrl = mockWebServer.url("/dummy").toString();
        log.info("Base URL: {}", baseUrl);

        String response = restClient.get()
                .uri(baseUrl).retrieve().body(String.class);

        log.info("Response: {}", response);

        // 응답 검증
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);

        if (jsonNode.isObject()) {
            log.info("Response is a JSON Object");
        } else if (jsonNode.isArray()) {
            log.info("Response is a JSON Array");
        } else {
            log.info("Response is not a valid JSON structure");
        }    }
}
