package com.catbot.cats;

import com.catbot.cats.dto.AddCatDto;
import com.catbot.cats.dto.CatDto;
import com.catbot.cats.dto.GetCatsDto;
import com.catbot.cats.entity.Cat;
import com.catbot.cats.entity.User;
import com.catbot.cats.repository.CatRepository;
import com.catbot.cats.repository.UserRepository;
import com.catbot.cats.service.CatService;
import com.catbot.cats.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
public class CatServiceIntegrationTest {
    
    @Autowired
    private CatService catService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CatRepository catRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void testFullWorkflow() {
        catRepository.deleteAll();
        userRepository.deleteAll();
        User user = userService.createOrGetUser(123L, "Тестовый", "Пользователь", "test_user");
        assertNotNull(user);
        assertEquals(123L, user.getTelegramId());
        AddCatDto addCatDto = new AddCatDto();
        addCatDto.setUserId(123L);
        addCatDto.setCatName("Тестовый котик");
        addCatDto.setPhotoUrl("test_photo_url");
        addCatDto.setDescription("Описание тестового котика");
        
        CatDto savedCat = catService.addCat(addCatDto);
        assertNotNull(savedCat);
        assertNotNull(savedCat.getId());
        assertEquals("Тестовый котик", savedCat.getName());
        GetCatsDto getCatsDto = new GetCatsDto();
        getCatsDto.setUserId(123L);
        getCatsDto.setOnlyMyCats(true);
        
        List<CatDto> userCats = catService.getCats(getCatsDto);
        assertNotNull(userCats);
        assertEquals(1, userCats.size());
        assertEquals("Тестовый котик", userCats.get(0).getName());
        getCatsDto.setOnlyMyCats(false);
        List<CatDto> randomCats = catService.getCats(getCatsDto);
        assertNotNull(randomCats);
        assertTrue(randomCats.size() >= 1); // должен быть хотя бы наш котик
        
        // 5. Удаляем котика
        catService.deleteCat(savedCat.getId(), 123L);
        
        // 6. Проверяем что котик удален
        getCatsDto.setOnlyMyCats(true);
        List<CatDto> catsAfterDelete = catService.getCats(getCatsDto);
        assertEquals(0, catsAfterDelete.size());
    }
    
    @Test
    void testUserCreationAndUpdate() {
        // Очищаем базу
        userRepository.deleteAll();
        
        // Создаем пользователя
        User user1 = userService.createOrGetUser(456L, "Иван", null, null);
        assertNotNull(user1);
        assertEquals("Иван", user1.getFirstName());
        assertNull(user1.getLastName());
        
        // Обновляем данные пользователя
        User user2 = userService.createOrGetUser(456L, "Игорь", "Петров", "igor_petrov");
        assertNotNull(user2);
        assertEquals("Игорь", user2.getFirstName());
        assertEquals("Петров", user2.getLastName());
        assertEquals("igor_petrov", user2.getUsername());
        
        // Проверяем что это тот же пользователь
        assertEquals(user1.getTelegramId(), user2.getTelegramId());
    }
} 