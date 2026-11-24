package com.gatherly.event.dto;

import java.time.Instant;

public record PatchEventUpdate(
        String title,
        String description,
        String location,
        Instant startAt,
        Instant endAt,
        Integer capacity
) {
}
