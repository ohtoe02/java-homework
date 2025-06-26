package com.catbot.telegram.service.impl;

import com.catbot.telegram.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

// Реализация отправки сообщений в Kafka
@Service
public class KafkaProducerImpl implements KafkaProducer {
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Value("${kafka.topic.commands}")
    private String commandsTopic;
    
    @Override
    public void sendEvent(String topic, Object event) {
        System.out.println("Отправляем событие в топик " + topic + ": " + event);
        
        try {
            kafkaTemplate.send(topic, event);
            System.out.println("Событие отправлено успешно!");
        } catch (Exception e) {
            System.err.println("Ошибка отправки в Kafka: " + e.getMessage());
        }
    }
    
    @Override
    public void sendCommand(Object command) {
        sendEvent(commandsTopic, command);
    }
} 