package com.catbot.telegram.service;

public interface UserStateService {
    
    String getUserState(Long userId);
    
    void setUserState(Long userId, String state);
    
    void saveUserData(Long userId, String key, Object value);
    
    Object getUserData(Long userId, String key);
    
    void clearUserData(Long userId);
} 