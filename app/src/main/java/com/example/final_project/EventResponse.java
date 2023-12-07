package com.example.final_project;

import com.example.final_project.models.Event;

import java.util.List;

public class EventResponse {
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
