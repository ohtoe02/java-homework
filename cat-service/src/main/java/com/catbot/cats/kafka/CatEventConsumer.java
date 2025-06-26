package com.catbot.cats.kafka;

import com.catbot.cats.dto.AddCatDto;
import com.catbot.cats.dto.CatDto;
import com.catbot.cats.dto.GetCatsDto;
import com.catbot.cats.dto.LikeCatDto;
import com.catbot.cats.service.CatService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

// Потребитель событий из Kafka
@Component
public class CatEventConsumer {
    
    @Autowired
    private CatService catService;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @KafkaListener(topics = "${kafka.topic.commands}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleCatCommand(@Payload String message, 
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                Acknowledgment ack) {
        
        System.out.println("Получено сообщение из топика " + topic + ": " + message);
        
        try {
            // Парсим JSON сообщение
            JsonNode eventNode = objectMapper.readTree(message);
            String eventType = eventNode.get("eventType").asText();
            
            System.out.println("Тип события: " + eventType);
            
            // Обрабатываем в зависимости от типа
            switch (eventType) {
                case "ADD_CAT":
                    handleAddCat(message);
                    break;
                case "GET_CATS":
                    handleGetCats(message);
                    break;
                case "LIKE_CAT":
                    handleLikeCat(message);
                    break;
                default:
                    System.err.println("Неизвестный тип события: " + eventType);
            }
            
            // Подтверждаем обработку
            ack.acknowledge();
            
        } catch (Exception e) {
            System.err.println("Ошибка обработки события: " + e.getMessage());
            e.printStackTrace();
            // TODO: добавить retry логику или отправку в DLQ
            ack.acknowledge(); // Подтверждаем чтобы не зависнуть
        }
    }
    
    private void handleAddCat(String eventData) {
        try {
            // Десериализуем событие добавления котика
            JsonNode eventNode = objectMapper.readTree(eventData);
            
            AddCatDto addCatDto = new AddCatDto();
            addCatDto.setEventId(eventNode.get("eventId").asText());
            addCatDto.setUserId(eventNode.get("userId").asLong());
            addCatDto.setCatName(eventNode.get("catName").asText());
            addCatDto.setPhotoUrl(eventNode.get("photoUrl").asText());
            addCatDto.setDescription(eventNode.get("description").asText());
            
            // Добавляем котика через сервис
            CatDto savedCat = catService.addCat(addCatDto);
            System.out.println("Котик успешно добавлен: " + savedCat.getName());
            
        } catch (Exception e) {
            System.err.println("Ошибка добавления котика: " + e.getMessage());
        }
    }
    
    private void handleGetCats(String eventData) {
        try {
            // Десериализуем событие получения котиков
            JsonNode eventNode = objectMapper.readTree(eventData);
            
            GetCatsDto getCatsDto = new GetCatsDto();
            getCatsDto.setEventId(eventNode.get("eventId").asText());
            getCatsDto.setUserId(eventNode.get("userId").asLong());
            getCatsDto.setOnlyMyCats(eventNode.get("onlyMyCats").asBoolean());
            
            // Получаем котиков через сервис
            List<CatDto> cats = catService.getCats(getCatsDto);
            System.out.println("Найдено котиков: " + cats.size());
            
            // TODO: отправить ответ обратно в telegram-adapter через Kafka
            
        } catch (Exception e) {
            System.err.println("Ошибка получения котиков: " + e.getMessage());
        }
    }
    
    private void handleLikeCat(String eventData) {
        try {
            // Десериализуем событие лайка
            JsonNode eventNode = objectMapper.readTree(eventData);
            
            LikeCatDto likeCatDto = new LikeCatDto();
            likeCatDto.setEventId(eventNode.get("eventId").asText());
            likeCatDto.setUserId(eventNode.get("userId").asLong());
            likeCatDto.setCatId(eventNode.get("catId").asText());
            likeCatDto.setLike(eventNode.get("like").asBoolean());
            
            // Ставим лайк через сервис
            catService.likeCat(likeCatDto);
            System.out.println("Лайк обработан для котика: " + likeCatDto.getCatId());
            
        } catch (Exception e) {
            System.err.println("Ошибка лайка котика: " + e.getMessage());
        }
    }
} 