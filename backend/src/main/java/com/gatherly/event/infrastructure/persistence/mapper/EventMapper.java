package com.gatherly.event.infrastructure.persistence.mapper;

import com.gatherly.event.domain.model.Event;
import com.gatherly.event.infrastructure.persistence.entity.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventEntity toEntity(Event event) {
        return new EventEntity(
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getStartAt(),
                event.getEndAt(),
                event.getCapacity()
        );
    }

    public Event toDomain(EventEntity entity) {
        return new Event(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getLocation(),
                entity.getStartAt(),
                entity.getEndAt(),
                entity.getCapacity()
        );
    }
}
