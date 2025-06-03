package com.us.liveevents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.us.liveevents.enums.EventStatus;
import com.us.liveevents.model.EventStatusRequest;
import com.us.liveevents.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventStatusController.class)
class EventStatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void updateStatus_shouldCallService() throws Exception {
        EventStatusRequest req = new EventStatusRequest("evt1", EventStatus.LIVE);

        mockMvc.perform(post("/events/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)))
            .andExpect(status().isOk());

        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<EventStatus> statusCaptor = ArgumentCaptor.forClass(EventStatus.class);
        verify(eventService).updateEventStatus(idCaptor.capture(), statusCaptor.capture());

        assertThat(idCaptor.getValue()).isEqualTo("evt1");
        assertThat(statusCaptor.getValue()).isEqualTo(EventStatus.LIVE);
    }
}
