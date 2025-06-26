package com.catbot.cats.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
public class CatEventConsumer {
    
    @KafkaListener(topics = "${kafka.topic.commands}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleCatCommand(@Payload String message, 
                                @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                Acknowledgment ack) {
        
        System.out.println("Получено сообщение из топика " + topic + ": " + message);
        
        try {
            
            System.out.println("TODO: Обработать событие: " + message);
            ack.acknowledge();
            
        } catch (Exception e) {
            System.err.println("Ошибка обработки события: " + e.getMessage());
        }
    }
    
    private void handleAddCat(String eventData) {
        System.out.println("TODO: Добавить котика");
    }
    
    private void handleGetCats(String eventData) {
        System.out.println("TODO: Получить котиков");
    }
    
    private void handleLikeCat(String eventData) {
        System.out.println("TODO: Лайкнуть котика");
    }
} 