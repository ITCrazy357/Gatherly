package com.gatherly.event.application.validator;


import com.gatherly.event.domain.model.Event;

import java.time.Instant;

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

        if(event.getStartAt().isBefore(event.getEndAt())){
            throw new IllegalArgumentException("Start time cannot be before end time");
        }

        if(event.getEndAt().isAfter(event.getStartAt())){
            throw new IllegalArgumentException("End time cannot be after start time");
        }

        if(event.getCapacity() <= 0){
            throw new IllegalArgumentException("Capacity cannot be less than 0");
        }
    }
}
