package com.catbot.cats.repository;

import com.catbot.cats.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByTelegramId(Long telegramId);
    
    boolean existsByTelegramId(Long telegramId);
} 