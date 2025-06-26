package com.catbot.cats.repository;

import com.catbot.cats.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Репозиторий для работы с пользователями
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Найти пользователя по telegram ID
    User findByTelegramId(Long telegramId);
    
    // Проверить существует ли пользователь
    boolean existsByTelegramId(Long telegramId);
} 