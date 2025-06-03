package com.us.liveevents.model;

import com.us.liveevents.enums.EventStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventStatusRequest {

    @NotBlank
    @Schema(description = "Event unique identifier", example = "1234")
    private String eventId;
    @NotNull
    @Schema(description = "Status for the event", example = "LIVE")
    private EventStatus status;
}
