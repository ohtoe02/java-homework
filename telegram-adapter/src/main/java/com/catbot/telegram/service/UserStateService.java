package com.catbot.telegram.service;

// Сервис для управления состоянием пользователей
public interface UserStateService {
    
    // Получить текущее состояние пользователя
    String getUserState(Long userId);
    
    // Установить состояние пользователя
    void setUserState(Long userId, String state);
    
    // Сохранить данные пользователя
    void saveUserData(Long userId, String key, Object value);
    
    // Получить данные пользователя
    Object getUserData(Long userId, String key);
    
    // Очистить данные пользователя
    void clearUserData(Long userId);
} 