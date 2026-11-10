package com.gatherly.event.dto;

import java.time.Instant;

public record CreateEventRequest(
        String title,
        String description,
        String location,
        Instant startAt,
        Instant endAt,
        Integer capacity) {
}
