package com.catbot.cats.service;

import com.catbot.cats.dto.AddCatDto;
import com.catbot.cats.dto.CatDto;
import com.catbot.cats.dto.GetCatsDto;
import com.catbot.cats.dto.LikeCatDto;
import com.catbot.cats.entity.Cat;
import com.catbot.cats.entity.User;
import com.catbot.cats.repository.CatRepository;
import com.catbot.cats.service.impl.CatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Тесты для сервиса котиков
public class CatServiceTest {
    
    @Mock
    private CatRepository catRepository;
    
    @Mock
    private UserService userService;
    
    @Mock
    private CatMapper catMapper;
    
    @InjectMocks
    private CatServiceImpl catService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testAddCat() {
        // Подготовка данных
        AddCatDto addCatDto = new AddCatDto();
        addCatDto.setUserId(123L);
        addCatDto.setCatName("Мурзик");
        addCatDto.setPhotoUrl("photo123");
        addCatDto.setDescription("Красивый котик");
        
        User user = new User(123L, "Иван");
        Cat savedCat = new Cat("Мурзик", "photo123", "Красивый котик", 123L);
        savedCat.setId(1L);
        
        CatDto expectedDto = new CatDto();
        expectedDto.setId(1L);
        expectedDto.setName("Мурзик");
        
        // Мокаем вызовы
        when(userService.createOrGetUser(123L, "Unknown", null, null)).thenReturn(user);
        when(catRepository.save(any(Cat.class))).thenReturn(savedCat);
        when(catMapper.toDto(savedCat)).thenReturn(expectedDto);
        
        // Выполняем тест
        CatDto result = catService.addCat(addCatDto);
        
        // Проверяем результат
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Мурзик", result.getName());
        
        // Проверяем что методы были вызваны
        verify(userService).createOrGetUser(123L, "Unknown", null, null);
        verify(catRepository).save(any(Cat.class));
        verify(catMapper).toDto(savedCat);
    }
    
    @Test
    void testGetCatsForUser() {
        // Подготовка данных
        GetCatsDto getCatsDto = new GetCatsDto();
        getCatsDto.setUserId(123L);
        getCatsDto.setOnlyMyCats(true);
        
        Cat cat1 = new Cat("Мурзик", "photo1", "Котик 1", 123L);
        Cat cat2 = new Cat("Барсик", "photo2", "Котик 2", 123L);
        List<Cat> cats = Arrays.asList(cat1, cat2);
        
        CatDto dto1 = new CatDto();
        dto1.setName("Мурзик");
        CatDto dto2 = new CatDto();
        dto2.setName("Барсик");
        List<CatDto> expectedDtos = Arrays.asList(dto1, dto2);
        
        // Мокаем вызовы
        when(catRepository.findByOwnerIdOrderByCreatedAtDesc(123L)).thenReturn(cats);
        when(catMapper.toDtoList(cats)).thenReturn(expectedDtos);
        
        // Выполняем тест
        List<CatDto> result = catService.getCats(getCatsDto);
        
        // Проверяем результат
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Мурзик", result.get(0).getName());
        assertEquals("Барсик", result.get(1).getName());
        
        verify(catRepository).findByOwnerIdOrderByCreatedAtDesc(123L);
        verify(catMapper).toDtoList(cats);
    }
    
    @Test
    void testGetRandomCats() {
        // Подготовка данных
        GetCatsDto getCatsDto = new GetCatsDto();
        getCatsDto.setUserId(123L);
        getCatsDto.setOnlyMyCats(false);
        
        Cat cat1 = new Cat("Случайный котик", "photo1", "Описание", 456L);
        List<Cat> cats = Arrays.asList(cat1);
        
        CatDto dto1 = new CatDto();
        dto1.setName("Случайный котик");
        List<CatDto> expectedDtos = Arrays.asList(dto1);
        
        // Мокаем вызовы
        when(catRepository.findRandomCats(5)).thenReturn(cats);
        when(catMapper.toDtoList(cats)).thenReturn(expectedDtos);
        
        // Выполняем тест
        List<CatDto> result = catService.getCats(getCatsDto);
        
        // Проверяем результат
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Случайный котик", result.get(0).getName());
        
        verify(catRepository).findRandomCats(5);
        verify(catMapper).toDtoList(cats);
    }
    
    @Test
    void testLikeCat() {
        // Подготовка данных
        LikeCatDto likeCatDto = new LikeCatDto();
        likeCatDto.setCatId("1");
        likeCatDto.setLike(true);
        
        Cat cat = new Cat("Мурзик", "photo1", "Описание", 123L);
        cat.setId(1L);
        cat.setLikes(5);
        
        // Мокаем вызовы
        when(catRepository.findById(1L)).thenReturn(Optional.of(cat));
        when(catRepository.save(cat)).thenReturn(cat);
        
        // Выполняем тест
        catService.likeCat(likeCatDto);
        
        // Проверяем что лайки увеличились
        assertEquals(6, cat.getLikes());
        
        verify(catRepository).findById(1L);
        verify(catRepository).save(cat);
    }
    
    @Test
    void testDislikeCat() {
        // Подготовка данных
        LikeCatDto likeCatDto = new LikeCatDto();
        likeCatDto.setCatId("1");
        likeCatDto.setLike(false);
        
        Cat cat = new Cat("Мурзик", "photo1", "Описание", 123L);
        cat.setId(1L);
        cat.setDislikes(2);
        
        // Мокаем вызовы
        when(catRepository.findById(1L)).thenReturn(Optional.of(cat));
        when(catRepository.save(cat)).thenReturn(cat);
        
        // Выполняем тест
        catService.likeCat(likeCatDto);
        
        // Проверяем что дизлайки увеличились
        assertEquals(3, cat.getDislikes());
        
        verify(catRepository).findById(1L);
        verify(catRepository).save(cat);
    }
    
    @Test
    void testDeleteCat() {
        // Подготовка данных
        Cat cat = new Cat("Мурзик", "photo1", "Описание", 123L);
        cat.setId(1L);
        
        // Мокаем вызовы
        when(catRepository.findById(1L)).thenReturn(Optional.of(cat));
        
        // Выполняем тест
        catService.deleteCat(1L, 123L);
        
        // Проверяем что котик удален
        verify(catRepository).findById(1L);
        verify(catRepository).delete(cat);
    }
    
    @Test
    void testDeleteCatNotOwner() {
        // Подготовка данных
        Cat cat = new Cat("Мурзик", "photo1", "Описание", 456L); // другой владелец
        cat.setId(1L);
        
        // Мокаем вызовы
        when(catRepository.findById(1L)).thenReturn(Optional.of(cat));
        
        // Выполняем тест
        catService.deleteCat(1L, 123L); // пытается удалить пользователь 123
        
        // Проверяем что котик НЕ удален
        verify(catRepository).findById(1L);
        verify(catRepository, never()).delete(cat);
    }
} 