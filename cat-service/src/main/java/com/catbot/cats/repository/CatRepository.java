package com.catbot.cats.repository;

import com.catbot.cats.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Репозиторий для работы с котиками
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    
    // Найти всех котиков пользователя
    List<Cat> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
    
    // Найти случайных котиков
    @Query(value = "SELECT * FROM cats ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
    List<Cat> findRandomCats(int limit);
    
    // Посчитать котиков пользователя
    int countByOwnerId(Long ownerId);
} 