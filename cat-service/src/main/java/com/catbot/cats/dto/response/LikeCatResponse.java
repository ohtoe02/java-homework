package com.catbot.cats.dto.response;

// Ответ на лайк котика
public class LikeCatResponse extends BaseResponse {
    
    private String catId;
    private boolean isLike;
    private int newLikes;
    private int newDislikes;
    
    public LikeCatResponse() {
        super();
        setResponseType("LIKE_CAT_RESPONSE");
    }
    
    public LikeCatResponse(String eventId, Long userId, String catId, boolean isLike, int newLikes, int newDislikes) {
        super(eventId, userId, "LIKE_CAT_RESPONSE");
        this.catId = catId;
        this.isLike = isLike;
        this.newLikes = newLikes;
        this.newDislikes = newDislikes;
    }
    
    // Геттеры и сеттеры
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
    
    public int getNewLikes() {
        return newLikes;
    }
    
    public void setNewLikes(int newLikes) {
        this.newLikes = newLikes;
    }
    
    public int getNewDislikes() {
        return newDislikes;
    }
    
    public void setNewDislikes(int newDislikes) {
        this.newDislikes = newDislikes;
    }
} 