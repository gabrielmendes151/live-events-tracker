package com.us.liveevents.controller;

import com.us.liveevents.model.EventData;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/mock-api/events")
public class MockApiController {

    @GetMapping("{eventId}")
    public EventData getEvent(@PathVariable final String eventId) {
        int home = new Random().nextInt(5);
        int away = new Random().nextInt(5);
        return new EventData(eventId, home + ":" + away);
    }
}
