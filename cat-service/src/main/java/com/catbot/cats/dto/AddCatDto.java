package com.catbot.cats.dto;

// DTO для добавления котика
public class AddCatDto {
    
    private String eventId;
    private Long userId;
    private String catName;
    private String photoUrl;
    private String description;
    
    // Конструкторы
    public AddCatDto() {
    }
    
    public AddCatDto(String eventId, Long userId, String catName, String photoUrl, String description) {
        this.eventId = eventId;
        this.userId = userId;
        this.catName = catName;
        this.photoUrl = photoUrl;
        this.description = description;
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
    
    public String getCatName() {
        return catName;
    }
    
    public void setCatName(String catName) {
        this.catName = catName;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
} 