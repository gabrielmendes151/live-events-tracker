package com.us.liveevents.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventStatus {

    LIVE("live"),
    NOT_LIVE("not live");

    private final String description;
}
