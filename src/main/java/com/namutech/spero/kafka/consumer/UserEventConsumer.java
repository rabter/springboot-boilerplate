package com.namutech.spero.kafka.consumer;

import com.namutech.spero.kafka.event.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserEventConsumer {

    @KafkaListener(
            topics = "user-created-topic",
            groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserCreated(UserCreatedEvent event) {
        // Kafka에서 UserCreatedEvent를 수신하여 처리
        // event.getUserId()는 생성된 사용자의 ID입니다.
        // event.getEmail()은 생성된 사용자의 이메일입니다.
        log.info("Received UserCreatedEvent: userId={}, email={}", event.getUserId(), event.getEmail());

        // 추가적인 비즈니스 로직을 여기에 작성할 수 있습니다.
    }
}
