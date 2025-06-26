package com.catbot.cats.service.impl;

import com.catbot.cats.dto.AddCatDto;
import com.catbot.cats.dto.CatDto;
import com.catbot.cats.dto.GetCatsDto;
import com.catbot.cats.dto.LikeCatDto;
import com.catbot.cats.entity.Cat;
import com.catbot.cats.entity.User;
import com.catbot.cats.repository.CatRepository;
import com.catbot.cats.service.CatMapper;
import com.catbot.cats.service.CatService;
import com.catbot.cats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Реализация сервиса котиков
@Service
public class CatServiceImpl implements CatService {
    
    @Autowired
    private CatRepository catRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CatMapper catMapper;
    
    @Override
    public CatDto addCat(AddCatDto addCatDto) {
        System.out.println("Добавляем котика: " + addCatDto.getCatName());
        
        // Проверяем/создаем пользователя
        User user = userService.createOrGetUser(addCatDto.getUserId(), "Unknown", null, null);
        
        // Создаем котика
        Cat cat = new Cat();
        cat.setName(addCatDto.getCatName());
        cat.setPhotoUrl(addCatDto.getPhotoUrl());
        cat.setDescription(addCatDto.getDescription());
        cat.setOwnerId(addCatDto.getUserId());
        
        // Сохраняем в базу
        Cat savedCat = catRepository.save(cat);
        
        System.out.println("Котик добавлен с ID: " + savedCat.getId());
        
        return catMapper.toDto(savedCat);
    }
    
    @Override
    public List<CatDto> getCats(GetCatsDto getCatsDto) {
        System.out.println("Получаем котиков для пользователя: " + getCatsDto.getUserId());
        
        List<Cat> cats;
        
        if (getCatsDto.isOnlyMyCats()) {
            // Получаем котиков пользователя
            cats = catRepository.findByOwnerIdOrderByCreatedAtDesc(getCatsDto.getUserId());
            System.out.println("Найдено котиков пользователя: " + cats.size());
        } else {
            // Получаем случайных котиков
            cats = catRepository.findRandomCats(5); // возвращаем 5 случайных котиков
            System.out.println("Найдено случайных котиков: " + cats.size());
        }
        
        return catMapper.toDtoList(cats);
    }
    
    @Override
    public void likeCat(LikeCatDto likeCatDto) {
        System.out.println("Обрабатываем лайк котика: " + likeCatDto.getCatId());
        
        try {
            Long catId = Long.parseLong(likeCatDto.getCatId());
            Optional<Cat> catOpt = catRepository.findById(catId);
            
            if (catOpt.isPresent()) {
                Cat cat = catOpt.get();
                
                if (likeCatDto.isLike()) {
                    cat.setLikes(cat.getLikes() + 1);
                    System.out.println("Добавлен лайк котику: " + cat.getName());
                } else {
                    cat.setDislikes(cat.getDislikes() + 1);
                    System.out.println("Добавлен дизлайк котику: " + cat.getName());
                }
                
                catRepository.save(cat);
            } else {
                System.err.println("Котик не найден с ID: " + catId);
            }
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат ID котика: " + likeCatDto.getCatId());
        }
    }
    
    @Override
    public void deleteCat(Long catId, Long userId) {
        System.out.println("Удаляем котика: " + catId + " пользователем: " + userId);
        
        Optional<Cat> catOpt = catRepository.findById(catId);
        
        if (catOpt.isPresent()) {
            Cat cat = catOpt.get();
            
            // Проверяем что котик принадлежит пользователю
            if (cat.getOwnerId().equals(userId)) {
                catRepository.delete(cat);
                System.out.println("Котик успешно удален: " + cat.getName());
            } else {
                System.err.println("Пользователь не может удалить чужого котика");
            }
        } else {
            System.err.println("Котик не найден с ID: " + catId);
        }
    }
    
    @Override
    public CatDto getCatById(Long catId) {
        Optional<Cat> catOpt = catRepository.findById(catId);
        
        if (catOpt.isPresent()) {
            return catMapper.toDto(catOpt.get());
        }
        
        System.err.println("Котик не найден с ID: " + catId);
        return null;
    }
} 