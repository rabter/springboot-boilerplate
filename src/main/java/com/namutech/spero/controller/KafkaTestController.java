package com.namutech.spero.controller;

import com.namutech.spero.kafka.event.UserCreatedEvent;
import com.namutech.spero.kafka.producer.UserEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Profile("kafka") // 'kafka' 프로파일에서만 활성화
@Slf4j
public class KafkaTestController {
    private final UserEventProducer producer;

    @GetMapping("/kafka/test-user")
    public String testUser() {
        // 테스트용 사용자 생성
        log.info("Kafka Test User Endpoint accessed");
        return "Kafka Test User Endpoint";
    }

    @PostMapping("/kafka/test-user")
    public String createUser() {
        // UserCreatedEvent를 생성하고 Kafka로 전송
        // 실제 사용 시 적절한 사용자 ID와 이메일을 설정해야 합니다.
        log.info("Sending UserCreatedEvent to Kafka...");
        UserCreatedEvent event = new UserCreatedEvent(UUID.randomUUID().toString(), "test@example.com");
        producer.sendUserCreatedEvent(event);

        return "UserCreatedEvent sent to Kafka: " + event.getUserId() + ", " + event.getEmail();
    }
}
