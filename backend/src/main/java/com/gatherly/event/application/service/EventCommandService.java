package com.gatherly.event.application.service;

import com.gatherly.event.application.validator.EventValidator;
import com.gatherly.event.domain.model.Event;
import com.gatherly.event.domain.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service
public class EventCommandService {

    private final EventRepository eventRepository;
    private final EventValidator eventValidator;

    public EventCommandService(
            EventRepository eventRepository,
            EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.eventValidator = eventValidator;
    }

    public Event create(Event event) {
        eventValidator.validate(event);
        return eventRepository.save(event);
    }
}
