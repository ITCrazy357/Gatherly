package com.gatherly.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record CreateEventRequest(
        @NotBlank
        @Size(max = 150)
        String title,

        @NotBlank
        String description,

        @NotBlank
        String location,

        @NotNull
        Instant startAt,

        @NotNull
        Instant endAt,

        @NotNull
        @Positive
        Integer capacity
) {
}
