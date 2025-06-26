package com.catbot.telegram.service;

public interface KafkaProducer {
    
    void sendEvent(String topic, Object event);
    
    void sendCommand(Object command);
} 