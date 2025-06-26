package com.catbot.cats.service;

import com.catbot.cats.dto.AddCatDto;
import com.catbot.cats.dto.CatDto;
import com.catbot.cats.dto.GetCatsDto;
import com.catbot.cats.dto.LikeCatDto;

import java.util.List;

public interface CatService {
    
    CatDto addCat(AddCatDto addCatDto);
    
    List<CatDto> getCats(GetCatsDto getCatsDto);
    
    void likeCat(LikeCatDto likeCatDto);
    
    void deleteCat(Long catId, Long userId);
    
    CatDto getCatById(Long catId);
} 