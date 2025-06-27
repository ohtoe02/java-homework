package com.catbot.telegram.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
public class ResponseConsumer {
    
    @Autowired
    private TelegramResponseService telegramResponseService;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @KafkaListener(topics = "cat-responses", groupId = "telegram-adapter-responses")
    public void handleResponse(@Payload String message, 
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              Acknowledgment ack) {
        
        System.out.println("Получен ответ из топика " + topic + ": " + message);
        
        try {
            JsonNode responseNode = objectMapper.readTree(message);
            String responseType = responseNode.get("responseType").asText();
            
            System.out.println("Тип ответа: " + responseType);
            switch (responseType) {
                case "ADD_CAT_RESPONSE":
                    telegramResponseService.handleAddCatResponse(message);
                    break;
                case "GET_CATS_RESPONSE":
                    telegramResponseService.handleGetCatsResponse(message);
                    break;
                case "LIKE_CAT_RESPONSE":
                    telegramResponseService.handleLikeCatResponse(message);
                    break;
                default:
                    System.err.println("Неизвестный тип ответа: " + responseType);
            }
            
            ack.acknowledge();
            
        } catch (Exception e) {
            System.err.println("Ошибка обработки ответа: " + e.getMessage());
            e.printStackTrace();
            ack.acknowledge();
        }
    }
} 