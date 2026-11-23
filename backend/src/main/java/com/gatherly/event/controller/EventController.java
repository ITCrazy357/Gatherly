package com.gatherly.event.controller;

import com.gatherly.event.application.service.EventCommandService;
import com.gatherly.event.application.service.EventQueryService;
import com.gatherly.event.domain.model.Event;
import com.gatherly.event.dto.CreateEventRequest;
import com.gatherly.event.dto.EventResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventCommandService eventCommandService;
    private final EventQueryService eventQueryService;

    public EventController(
            EventCommandService eventCommandService,
            EventQueryService eventQueryService
    ) {
        this.eventCommandService = eventCommandService;
        this.eventQueryService = eventQueryService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> create(
            @Valid @RequestBody CreateEventRequest request
    ) {
        Event event = new Event(
                null,
                request.title(),
                request.description(),
                request.location(),
                request.startAt(),
                request.endAt(),
                request.capacity()
        );

        Event createdEvent = eventCommandService.create(event);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(EventResponse.from(createdEvent));
    }

    @GetMapping
    public List<EventResponse> findAll() {
        return eventQueryService.findAll()
                .stream()
                .map(EventResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public EventResponse findById(@PathVariable Long id) {
        Event event = eventQueryService.findById(id);
        return EventResponse.from(event);
    }
}