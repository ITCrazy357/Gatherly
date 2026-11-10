package com.gatherly.event.model;

import java.time.Instant;

public class Event {
    private Long id;

    private String title;
    private String description;
    private String location;

    private Instant startAt;
    private Instant endAt;

    private Integer capacity;

    public Event(
            Long id,
            String title,
            String description,
            String location,
            Instant startAt,
            Instant endAt,
            Integer capacity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.startAt = startAt;
        this.endAt = endAt;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public Instant getEndAt() {
        return endAt;
    }

    public Integer getCapacity() {
        return capacity;
    }
}
