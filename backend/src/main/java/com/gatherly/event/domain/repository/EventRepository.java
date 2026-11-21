package com.gatherly.event.domain.repository;

import org.springframework.stereotype.Repository;
import com.gatherly.event.domain.model.Event;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {
    private final Map<Long, Event> events = new ConcurrentHashMap<>(); //ConcurrentHashMap hỗ trợ truy cập đồng thời

    private final AtomicLong idSequence = new AtomicLong(0); //AtomicLong tăng ID an toàn trong môi trường nhiều thread

    public Event create(Event event) {
        Long id = idSequence.incrementAndGet();
        Event newEvent = new Event(id, event.getTitle(), event.getDescription(), event.getLocation(),
                event.getStartAt(), event.getEndAt(), event.getCapacity());
        events.put(id, newEvent);
        return newEvent;
    }

    public List<Event> findAll() {
        return List.copyOf(events.values());
    }

    public Optional<Event> findById(Long id) {
        return Optional.ofNullable(events.get(id));
    }

    public Optional<Event> update(long id, Event event) {
        Event updatedEvent = new Event(
                id,
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getStartAt(),
                event.getEndAt(),
                event.getCapacity());

        return Optional.ofNullable(events.computeIfPresent(id, (key, value) -> updatedEvent));
    }

    public boolean delete(Long id) {
        return events.remove(id) != null;
    }

    public Event save(Event event) {
        return event;
    }
}
