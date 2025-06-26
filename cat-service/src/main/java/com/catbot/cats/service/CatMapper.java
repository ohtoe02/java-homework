package com.catbot.cats.service;

import com.catbot.cats.dto.CatDto;
import com.catbot.cats.entity.Cat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// Маппер для преобразования Cat <-> CatDto
@Component
public class CatMapper {
    
    // Преобразовать Cat в CatDto
    public CatDto toDto(Cat cat) {
        if (cat == null) {
            return null;
        }
        
        CatDto dto = new CatDto();
        dto.setId(cat.getId());
        dto.setName(cat.getName());
        dto.setPhotoUrl(cat.getPhotoUrl());
        dto.setDescription(cat.getDescription());
        dto.setOwnerId(cat.getOwnerId());
        dto.setCreatedAt(cat.getCreatedAt());
        dto.setLikes(cat.getLikes());
        dto.setDislikes(cat.getDislikes());
        
        return dto;
    }
    
    // Преобразовать список Cat в список CatDto
    public List<CatDto> toDtoList(List<Cat> cats) {
        return cats.stream()
                   .map(this::toDto)
                   .collect(Collectors.toList());
    }
    
    // Преобразовать CatDto в Cat (для создания)
    public Cat toEntity(CatDto dto) {
        if (dto == null) {
            return null;
        }
        
        Cat cat = new Cat();
        cat.setName(dto.getName());
        cat.setPhotoUrl(dto.getPhotoUrl());
        cat.setDescription(dto.getDescription());
        cat.setOwnerId(dto.getOwnerId());
        
        return cat;
    }
} 