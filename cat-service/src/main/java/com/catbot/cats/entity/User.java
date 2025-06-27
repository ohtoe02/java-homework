package com.catbot.cats.entity;

import jakarta.persistence.*;

// Сущность пользователя
@Entity
@Table(name = "users")
public class User {
    
    @Id
    private Long telegramId;
    
    @Column(nullable = false)
    private String firstName;
    
    private String lastName;
    
    private String username;
    
    // Конструкторы
    public User() {
    }
    
    public User(Long telegramId, String firstName) {
        this.telegramId = telegramId;
        this.firstName = firstName;
    }
    
    // Геттеры и сеттеры
    public Long getTelegramId() {
        return telegramId;
    }
    
    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
} 