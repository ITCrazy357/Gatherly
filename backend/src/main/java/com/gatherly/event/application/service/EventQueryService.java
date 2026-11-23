package com.gatherly.event.application.service;

import com.gatherly.event.domain.exception.EventNotFoundException;
import com.gatherly.event.domain.model.Event;
import com.gatherly.event.domain.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventQueryService {
    private final EventRepository eventRepository;

    public EventQueryService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    public Event findById(Long id){
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }
}
