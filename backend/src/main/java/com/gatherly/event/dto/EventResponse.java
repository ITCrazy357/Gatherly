package com.gatherly.event.dto;

import com.gatherly.event.domain.model.Event;
import java.time.Instant;

public record EventResponse(
                Long id,
                String title,
                String description,
                String location,
                Instant startAt,
                Instant endAt,
                Integer capacity) {
        public static EventResponse from(Event event) {
                return new EventResponse(
                                event.getId(),
                                event.getTitle(),
                                event.getDescription(),
                                event.getLocation(),
                                event.getStartAt(),
                                event.getEndAt(),
                                event.getCapacity());
        }
}
