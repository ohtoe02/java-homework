package com.catbot.telegram.service.impl;

import com.catbot.telegram.domain.UserState;
import com.catbot.telegram.service.UserStateService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserStateServiceImpl implements UserStateService {
    
    private Map<Long, String> userStates = new HashMap<>();
    
    private Map<Long, Map<String, Object>> userData = new HashMap<>();
    
    @Override
    public String getUserState(Long userId) {
        return userStates.getOrDefault(userId, UserState.IDLE);
    }
    
    @Override
    public void setUserState(Long userId, String state) {
        userStates.put(userId, state);
        System.out.println("Установлено состояние для пользователя " + userId + ": " + state);
    }
    
    @Override
    public void saveUserData(Long userId, String key, Object value) {
        if (!userData.containsKey(userId)) {
            userData.put(userId, new HashMap<>());
        }
        userData.get(userId).put(key, value);
    }
    
    @Override
    public Object getUserData(Long userId, String key) {
        if (userData.containsKey(userId)) {
            return userData.get(userId).get(key);
        }
        return null;
    }
    
    @Override
    public void clearUserData(Long userId) {
        userData.remove(userId);
        userStates.put(userId, UserState.IDLE);
        System.out.println("Очищены данные пользователя " + userId);
    }
} 