package com.catbot.cats.kafka;

import com.catbot.cats.dto.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

// Producer для отправки ответов в telegram-adapter
@Component
public class ResponseProducer {
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.responses:cat-responses}")
    private String responsesTopic;
    
    // Отправить ответ в telegram-adapter
    public void sendResponse(BaseResponse response) {
        try {
            System.out.println("Отправляем ответ в топик " + responsesTopic + ": " + response.getResponseType());
            
            kafkaTemplate.send(responsesTopic, String.valueOf(response.getUserId()), response);
            
            System.out.println("Ответ отправлен успешно для пользователя: " + response.getUserId());
        } catch (Exception e) {
            System.err.println("Ошибка отправки ответа в Kafka: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 