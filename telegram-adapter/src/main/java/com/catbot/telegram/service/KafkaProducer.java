package com.catbot.telegram.service;

// Интерфейс для отправки сообщений в Kafka
public interface KafkaProducer {
    
    // Отправить событие в топик
    void sendEvent(String topic, Object event);
    
    // Отправить команду 
    void sendCommand(Object command);
} 