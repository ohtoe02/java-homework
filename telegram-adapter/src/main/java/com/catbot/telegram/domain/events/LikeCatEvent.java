package com.catbot.telegram.domain.events;

// Событие лайка/дизлайка котика
public class LikeCatEvent extends BaseEvent {
    
    private String catId;
    private boolean isLike; // true - лайк, false - дизлайк
    
    public LikeCatEvent() {
        super();
        setEventType("LIKE_CAT");
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