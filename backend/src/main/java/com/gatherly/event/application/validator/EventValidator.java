package com.gatherly.event.application.validator;

import com.gatherly.event.domain.model.Event;
import org.springframework.stereotype.Component;


@Component
public class EventValidator {

    public void validate(Event event) {
        if(event == null){
            throw new IllegalArgumentException("Event cannot be null");
        }

        if(event.getTitle() == null || event.getTitle().isBlank()){
            throw new IllegalArgumentException("Title cannot be blank");
        }

        if(event.getDescription() == null || event.getDescription().isBlank()){
            throw new IllegalArgumentException("Description cannot be blank");
        }

        if(event.getLocation() == null || event.getLocation().isBlank()){
            throw new IllegalArgumentException("Location cannot be blank");
        }

        if(event.getStartAt() == null || event.getEndAt() == null){
            throw new IllegalArgumentException("Start and end time cannot be null");
        }

        if (!event.getStartAt().isBefore(event.getEndAt())) {
            throw new IllegalArgumentException(
                    "Start time must be before end time"
            );
        }

        if (event.getCapacity() == null || event.getCapacity() <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
    }
}
