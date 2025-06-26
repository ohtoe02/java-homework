package com.catbot.cats.service;

import com.catbot.cats.entity.User;

public interface UserService {
    
    User createOrGetUser(Long telegramId, String firstName, String lastName, String username);
    
    User getUserByTelegramId(Long telegramId);
    
    boolean userExists(Long telegramId);
} 