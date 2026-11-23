package com.gatherly.event.infrastructure.adapter;

import com.gatherly.event.domain.model.Event;
import com.gatherly.event.domain.repository.EventRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRepositoryAdapter implements EventRepository {

    private final Map<Long, Event> events = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public Event save(Event event) {
        Long id = idSequence.incrementAndGet();

        Event newEvent = new Event(
                id,
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getStartAt(),
                event.getEndAt(),
                event.getCapacity()
        );

        events.put(id, newEvent);
        return newEvent;
    }

    @Override
    public List<Event> findAll() {
        return List.copyOf(events.values());
    }

    @Override
    public Optional<Event> findById(Long id) {
        return Optional.ofNullable(events.get(id));
    }

    @Override
    public Optional<Event> update(Long id, Event event) {
        Event updatedEvent = new Event(
                id,
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getStartAt(),
                event.getEndAt(),
                event.getCapacity()
        );

        return Optional.ofNullable(
                events.computeIfPresent(id, (key, value) -> updatedEvent)
        );
    }

    @Override
    public boolean delete(Long id) {
        return events.remove(id) != null;
    }
}