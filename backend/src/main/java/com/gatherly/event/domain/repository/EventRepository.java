package com.gatherly.event.domain.repository;

import com.gatherly.event.domain.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository {

    Event save(Event event);

    List<Event> findAll();

    Optional<Event> findById(Long id);

    Optional<Event> update(Long id, Event event);

    boolean delete(Long id);
}