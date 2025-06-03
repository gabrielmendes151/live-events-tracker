package com.us.liveevents.controller;

import com.us.liveevents.model.EventStatusRequest;
import com.us.liveevents.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventStatusController {

    private final EventService eventService;

    @PostMapping("status")
    public ResponseEntity<Void> updateStatus(@RequestBody @Valid final EventStatusRequest request) {
        eventService.updateEventStatus(request.getEventId(), request.getStatus());
        return ResponseEntity.ok().build();
    }
}
