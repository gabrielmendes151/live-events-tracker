package com.us.liveevents.kafka;

import com.us.liveevents.model.EventData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.name}")
    private String topic;

    public void publish(final EventData eventData) throws Exception {
        var message = objectMapper.writeValueAsString(eventData);
        int attempts = 0;
        Exception lastException = null;
        while (attempts < 3) {
            try {
                kafkaTemplate.send(topic, message).get();
                log.info("Published event {} to Kafka: {}", eventData.getEventId(), message);
                return;
            } catch (Exception e) {
                lastException = e;
                attempts++;
                log.error("Kafka publish failed for event {}, attempt {}: {}",
                    eventData.getEventId(), attempts, e.getMessage());
            }
        }
        log.error("Failed to publish event {} to Kafka after 3 attempts", eventData.getEventId());
        throw new RuntimeException("Failed to publish to Kafka after 3 attempts", lastException);
    }
}
