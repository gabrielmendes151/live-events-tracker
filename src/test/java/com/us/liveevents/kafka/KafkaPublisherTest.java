package com.us.liveevents.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.us.liveevents.model.EventData;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

class KafkaPublisherTest {

    @Test
    void publish_shouldSucceed() throws Exception {
        KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaPublisher publisher = new KafkaPublisher(kafkaTemplate, objectMapper);
        var event = new EventData("test", "0:0");

        SendResult<String, String> sendResult = mock(SendResult.class);

        CompletableFuture<SendResult<String, String>> successFuture = new CompletableFuture<>();
        successFuture.complete(sendResult);

        when(kafkaTemplate.send(any(), anyString())).thenReturn(successFuture);

        publisher.publish(event);

        verify(kafkaTemplate, times(1)).send(any(), anyString());
    }

    @Test
    void publish_shouldRetryOnFailure() {
        KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
        ObjectMapper objectMapper = new ObjectMapper();
        KafkaPublisher publisher = new KafkaPublisher(kafkaTemplate, objectMapper);
        var event = new EventData("test", "0:0");

        CompletableFuture<SendResult<String, String>> failedFuture = new CompletableFuture<>();
        failedFuture.completeExceptionally(new RuntimeException("fail"));

        when(kafkaTemplate.send(any(), anyString())).thenReturn(failedFuture);

        try {
            publisher.publish(event);
        } catch (Exception e) {
        }

        verify(kafkaTemplate, times(3)).send(any(), anyString());
    }
}
