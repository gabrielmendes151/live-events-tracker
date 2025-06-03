package com.us.liveevents.service;

import com.us.liveevents.enums.EventStatus;
import com.us.liveevents.scheduler.SchedulerService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class EventService {

    private final Map<String, EventStatus> liveEvents = new ConcurrentHashMap<>();

    public void updateEventStatus(final String eventId, final EventStatus status) {
        liveEvents.put(eventId, status);
    }

    public List<String> getLiveEvents() {
        return liveEvents.entrySet().stream()
            .filter(entry -> entry.getValue() == EventStatus.LIVE)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
}
