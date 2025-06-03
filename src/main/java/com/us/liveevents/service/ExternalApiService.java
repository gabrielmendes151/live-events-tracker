package com.us.liveevents.service;

import com.us.liveevents.model.EventData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExternalApiService {

    @Value("${external.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public EventData fetchEventData(final String eventId) {
        var url = apiUrl + eventId;
        ResponseEntity<EventData> response = restTemplate.getForEntity(url, EventData.class);
        return response.getBody();
    }
}
