package com.namutech.spero.kafka.producer;

import com.namutech.spero.kafka.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("kafka") // 'kafka' 프로파일에서만 활성화
public class UserEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserCreatedEvent(UserCreatedEvent event) {
        // Kafka 토픽에 UserCreatedEvent를 전송
        // user-created-topic 은 Kafka에서 사용자 생성 이벤트를 처리하기 위한 토픽입니다.
        // event.getUserId()는 메시지 키로 사용되며, 이를 통해 파티션을 결정합니다.
        // UserCreatedEvent는 Kafka 메시지로 직렬화되어 전송됩니다.
        kafkaTemplate.send("user-created-topic", event.getUserId(), event);
        log.info("Sent UserCreatedEvent to Kafka: userId={}, email={}", event.getUserId(), event.getEmail());
    }
}
