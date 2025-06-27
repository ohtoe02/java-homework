package com.catbot.cats.dto;

import java.time.LocalDateTime;

// DTO для представления котика
public class CatDto {
    
    private Long id;
    private String name;
    private String photoUrl;
    private String description;
    private Long ownerId;
    private LocalDateTime createdAt;
    private int likes;
    private int dislikes;
    
    // Конструкторы
    public CatDto() {
    }
    
    public CatDto(Long id, String name, String photoUrl, String description, 
                  Long ownerId, LocalDateTime createdAt, int likes, int dislikes) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.description = description;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.likes = likes;
        this.dislikes = dislikes;
    }
    
    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
    
    public Long getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public int getLikes() {
        return likes;
    }
    
    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    public int getDislikes() {
        return dislikes;
    }
    
    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }
} 