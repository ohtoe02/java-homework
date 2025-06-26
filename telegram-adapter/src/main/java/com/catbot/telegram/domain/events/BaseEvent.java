package com.catbot.telegram.domain.events;

import java.time.LocalDateTime;

public class BaseEvent {
    
    private String eventId;
    private Long userId;
    private LocalDateTime timestamp;
    private String eventType;
    
    public BaseEvent() {
        this.timestamp = LocalDateTime.now();
    }
    public String getEventId() {
        return eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
} 