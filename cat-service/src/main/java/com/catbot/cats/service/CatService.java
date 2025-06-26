package com.catbot.cats.service;

import com.catbot.cats.dto.AddCatDto;
import com.catbot.cats.dto.CatDto;
import com.catbot.cats.dto.GetCatsDto;
import com.catbot.cats.dto.LikeCatDto;

import java.util.List;

// Сервис для работы с котиками
public interface CatService {
    
    // Добавить нового котика
    CatDto addCat(AddCatDto addCatDto);
    
    // Получить котиков (случайных или пользователя)
    List<CatDto> getCats(GetCatsDto getCatsDto);
    
    // Поставить лайк/дизлайк котику
    void likeCat(LikeCatDto likeCatDto);
    
    // Удалить котика
    void deleteCat(Long catId, Long userId);
    
    // Получить котика по ID
    CatDto getCatById(Long catId);
} 