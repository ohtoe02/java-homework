package com.catbot.cats.dto;

// DTO для запроса котиков
public class GetCatsDto {
    
    private String eventId;
    private Long userId;
    private boolean onlyMyCats;
    
    // Конструкторы
    public GetCatsDto() {
    }
    
    public GetCatsDto(String eventId, Long userId, boolean onlyMyCats) {
        this.eventId = eventId;
        this.userId = userId;
        this.onlyMyCats = onlyMyCats;
    }
    
    // Геттеры и сеттеры
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
    
    public boolean isOnlyMyCats() {
        return onlyMyCats;
    }
    
    public void setOnlyMyCats(boolean onlyMyCats) {
        this.onlyMyCats = onlyMyCats;
    }
} 