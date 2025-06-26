package com.catbot.cats.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Сущность котика
@Entity
@Table(name = "cats")
public class Cat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String photoUrl;
    
    private String description;
    
    @Column(nullable = false)
    private Long ownerId; // telegram id владельца
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private int likes = 0;
    private int dislikes = 0;
    
    // Конструкторы
    public Cat() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Cat(String name, String photoUrl, String description, Long ownerId) {
        this();
        this.name = name;
        this.photoUrl = photoUrl;
        this.description = description;
        this.ownerId = ownerId;
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