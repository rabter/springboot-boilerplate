package com.namutech.spero.controller;

import com.namutech.spero.kafka.event.UserCreatedEvent;
import com.namutech.spero.kafka.producer.UserEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class KafkaTestController {
    private final UserEventProducer producer;

    @PostMapping("/kafka/test-user")
    public String createUser() {
        // UserCreatedEvent를 생성하고 Kafka로 전송
        // 실제 사용 시 적절한 사용자 ID와 이메일을 설정해야 합니다.
        UserCreatedEvent event = new UserCreatedEvent(UUID.randomUUID().toString(), "test@example.com");
        producer.sendUserCreatedEvent(event);

        return "UserCreatedEvent sent to Kafka: " + event.getUserId() + ", " + event.getEmail();
    }
}
