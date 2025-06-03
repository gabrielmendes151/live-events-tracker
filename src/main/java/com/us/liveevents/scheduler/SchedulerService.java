package com.us.liveevents.scheduler;

import com.us.liveevents.kafka.KafkaPublisher;
import com.us.liveevents.model.EventData;
import com.us.liveevents.service.EventService;
import com.us.liveevents.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final ExternalApiService apiService;
    private final KafkaPublisher kafkaPublisher;
    private final EventService eventService;

    @Scheduled(fixedRate = 10000)
    public void pollLiveEvents() {
        for (String eventId : eventService.getLiveEvents()) {
            try {
                var eventData = apiService.fetchEventData(eventId);
                kafkaPublisher.publish(eventData);
                log.info("Published data for event {}", eventId);
            } catch (Exception ex) {
                log.error("Error processing event {}: {}", eventId, ex.getMessage());
            }
        }
    }
}
