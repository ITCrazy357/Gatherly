package com.gatherly.event.infrastructure.adapter;

import com.gatherly.event.domain.repository.EventRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EventRepositoryAdapter extends EventRepository {

    public EventRepositoryAdapter(EventRepository eventRepository) {

    }
}
