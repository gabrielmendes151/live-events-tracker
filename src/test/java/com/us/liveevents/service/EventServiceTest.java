package com.us.liveevents.service;

import com.us.liveevents.enums.EventStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EventServiceTest {

    @Test
    void updateEventStatus_shouldUpdateMap() {
        EventService eventService = new EventService();

        eventService.updateEventStatus("evt1", EventStatus.LIVE);
        eventService.updateEventStatus("evt2", EventStatus.NOT_LIVE);

        List<String> liveEvents = eventService.getLiveEvents();

        assertThat(liveEvents).containsExactly("evt1");
    }
}
