package com.catbot.cats.service;

import com.catbot.cats.entity.User;
import com.catbot.cats.repository.UserRepository;
import com.catbot.cats.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Тесты для сервиса пользователей
public class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCreateNewUser() {
        // Подготовка данных
        Long telegramId = 123L;
        String firstName = "Иван";
        String lastName = "Петров";
        String username = "ivan_petrov";
        
        User savedUser = new User(telegramId, firstName);
        savedUser.setLastName(lastName);
        savedUser.setUsername(username);
        
        // Мокаем вызовы
        when(userRepository.findByTelegramId(telegramId)).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        
        // Выполняем тест
        User result = userService.createOrGetUser(telegramId, firstName, lastName, username);
        
        // Проверяем результат
        assertNotNull(result);
        assertEquals(telegramId, result.getTelegramId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(username, result.getUsername());
        
        // Проверяем что методы были вызваны
        verify(userRepository).findByTelegramId(telegramId);
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void testGetExistingUserNoUpdate() {
        // Подготовка данных
        Long telegramId = 123L;
        String firstName = "Иван";
        String lastName = "Петров";
        String username = "ivan_petrov";
        
        User existingUser = new User(telegramId, firstName);
        existingUser.setLastName(lastName);
        existingUser.setUsername(username);
        
        // Мокаем вызовы
        when(userRepository.findByTelegramId(telegramId)).thenReturn(existingUser);
        
        // Выполняем тест
        User result = userService.createOrGetUser(telegramId, firstName, lastName, username);
        
        // Проверяем результат
        assertNotNull(result);
        assertEquals(telegramId, result.getTelegramId());
        assertEquals(firstName, result.getFirstName());
        
        // Проверяем что save НЕ был вызван (данные не изменились)
        verify(userRepository).findByTelegramId(telegramId);
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void testUpdateExistingUser() {
        // Подготовка данных
        Long telegramId = 123L;
        String oldFirstName = "Иван";
        String newFirstName = "Игорь";
        String lastName = "Петров";
        String username = "ivan_petrov";
        
        User existingUser = new User(telegramId, oldFirstName);
        existingUser.setLastName(lastName);
        existingUser.setUsername(username);
        
        // Мокаем вызовы
        when(userRepository.findByTelegramId(telegramId)).thenReturn(existingUser);
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        
        // Выполняем тест
        User result = userService.createOrGetUser(telegramId, newFirstName, lastName, username);
        
        // Проверяем результат
        assertNotNull(result);
        assertEquals(telegramId, result.getTelegramId());
        assertEquals(newFirstName, result.getFirstName()); // имя должно обновиться
        
        // Проверяем что save был вызван (данные изменились)
        verify(userRepository).findByTelegramId(telegramId);
        verify(userRepository).save(existingUser);
    }
    
    @Test
    void testGetUserByTelegramId() {
        // Подготовка данных
        Long telegramId = 123L;
        User user = new User(telegramId, "Иван");
        
        // Мокаем вызовы
        when(userRepository.findByTelegramId(telegramId)).thenReturn(user);
        
        // Выполняем тест
        User result = userService.getUserByTelegramId(telegramId);
        
        // Проверяем результат
        assertNotNull(result);
        assertEquals(telegramId, result.getTelegramId());
        assertEquals("Иван", result.getFirstName());
        
        verify(userRepository).findByTelegramId(telegramId);
    }
    
    @Test
    void testUserExists() {
        // Подготовка данных
        Long telegramId = 123L;
        
        // Мокаем вызовы
        when(userRepository.existsByTelegramId(telegramId)).thenReturn(true);
        
        // Выполняем тест
        boolean result = userService.userExists(telegramId);
        
        // Проверяем результат
        assertTrue(result);
        
        verify(userRepository).existsByTelegramId(telegramId);
    }
    
    @Test
    void testUserNotExists() {
        // Подготовка данных
        Long telegramId = 123L;
        
        // Мокаем вызовы
        when(userRepository.existsByTelegramId(telegramId)).thenReturn(false);
        
        // Выполняем тест
        boolean result = userService.userExists(telegramId);
        
        // Проверяем результат
        assertFalse(result);
        
        verify(userRepository).existsByTelegramId(telegramId);
    }
} 