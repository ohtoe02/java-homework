package com.catbot.cats.service.impl;

import com.catbot.cats.entity.User;
import com.catbot.cats.repository.UserRepository;
import com.catbot.cats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Реализация сервиса пользователей
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User createOrGetUser(Long telegramId, String firstName, String lastName, String username) {
        // Проверяем есть ли уже такой пользователь
        User existingUser = userRepository.findByTelegramId(telegramId);
        
        if (existingUser != null) {
            // Обновляем данные если они изменились
            boolean needUpdate = false;
            
            if (!firstName.equals(existingUser.getFirstName())) {
                existingUser.setFirstName(firstName);
                needUpdate = true;
            }
            
            if (lastName != null && !lastName.equals(existingUser.getLastName())) {
                existingUser.setLastName(lastName);
                needUpdate = true;
            }
            
            if (username != null && !username.equals(existingUser.getUsername())) {
                existingUser.setUsername(username);
                needUpdate = true;
            }
            
            if (needUpdate) {
                userRepository.save(existingUser);
                System.out.println("Обновлены данные пользователя: " + telegramId);
            }
            
            return existingUser;
        }
        
        // Создаем нового пользователя
        User newUser = new User(telegramId, firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(username);
        
        User savedUser = userRepository.save(newUser);
        System.out.println("Создан новый пользователь: " + telegramId);
        
        return savedUser;
    }
    
    @Override
    public User getUserByTelegramId(Long telegramId) {
        return userRepository.findByTelegramId(telegramId);
    }
    
    @Override
    public boolean userExists(Long telegramId) {
        return userRepository.existsByTelegramId(telegramId);
    }
} 