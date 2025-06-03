package com.us.liveevents.scheduler;

import com.us.liveevents.kafka.KafkaPublisher;
import com.us.liveevents.model.EventData;
import com.us.liveevents.service.EventService;
import com.us.liveevents.service.ExternalApiService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SchedulerServiceTest {

    @Test
    void pollLiveEvents_shouldCallApiAndPublish() throws Exception {
        EventService eventService = mock(EventService.class);
        ExternalApiService apiService = mock(ExternalApiService.class);
        KafkaPublisher kafkaPublisher = mock(KafkaPublisher.class);

        SchedulerService scheduler = new SchedulerService(apiService, kafkaPublisher, eventService);

        when(eventService.getLiveEvents()).thenReturn(List.of("evt-1"));
        when(apiService.fetchEventData("evt-1")).thenReturn(new EventData("evt-1", "2:1"));

        scheduler.pollLiveEvents();

        verify(apiService).fetchEventData("evt-1");
        verify(kafkaPublisher).publish(any(EventData.class));
    }
}
