package com.catbot.cats.service;

import com.catbot.cats.entity.User;

// Сервис для работы с пользователями
public interface UserService {
    
    // Создать или получить пользователя
    User createOrGetUser(Long telegramId, String firstName, String lastName, String username);
    
    // Получить пользователя по telegram ID
    User getUserByTelegramId(Long telegramId);
    
    // Проверить существует ли пользователь
    boolean userExists(Long telegramId);
} 