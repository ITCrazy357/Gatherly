package com.gatherly.event.application.service;

import com.gatherly.event.application.validator.EventValidator;
import com.gatherly.event.domain.exception.EventNotFoundException;
import com.gatherly.event.domain.model.Event;
import com.gatherly.event.domain.repository.EventRepository;
import com.gatherly.event.dto.PatchEventUpdate;
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

    public Event update(Long id, Event event) {
        eventValidator.validate(event);

        return eventRepository.update(id, event)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    public Event patchUpdate(Long id, PatchEventUpdate request){
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        Event patchedEvent = new Event(
                id,
                request.title() != null
                        ? request.title()
                        : existingEvent.getTitle(),

                request.description() != null
                        ? request.description()
                        : existingEvent.getDescription(),

                request.location() != null
                        ? request.location()
                        : existingEvent.getLocation(),

                request.startAt() != null
                        ? request.startAt()
                        : existingEvent.getStartAt(),

                request.endAt() != null
                        ? request.endAt()
                        : existingEvent.getEndAt(),

                request.capacity() != null
                        ? request.capacity()
                        : existingEvent.getCapacity()
        );

        eventValidator.validate(patchedEvent);

        return eventRepository.update(id, patchedEvent).orElseThrow(() -> new EventNotFoundException(id));
    }

    public void delete(Long id){
        boolean deleted = eventRepository.delete(id);

        if(!deleted) {
            throw new EventNotFoundException(id);
        }
    }
}
