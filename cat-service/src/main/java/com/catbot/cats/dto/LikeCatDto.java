package com.catbot.cats.dto;

// DTO для лайка/дизлайка котика
public class LikeCatDto {
    
    private String eventId;
    private Long userId;
    private String catId;
    private boolean isLike;
    
    // Конструкторы
    public LikeCatDto() {
    }
    
    public LikeCatDto(String eventId, Long userId, String catId, boolean isLike) {
        this.eventId = eventId;
        this.userId = userId;
        this.catId = catId;
        this.isLike = isLike;
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
    
    public String getCatId() {
        return catId;
    }
    
    public void setCatId(String catId) {
        this.catId = catId;
    }
    
    public boolean isLike() {
        return isLike;
    }
    
    public void setLike(boolean like) {
        isLike = like;
    }
} 