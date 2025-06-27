package com.catbot.telegram.domain.events;

// Событие добавления нового котика
public class AddCatEvent extends BaseEvent {
    
    private String catName;
    private String photoUrl;
    private String description;
    
    public AddCatEvent() {
        super();
        setEventType("ADD_CAT");
    }
    
    // Геттеры и сеттеры
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