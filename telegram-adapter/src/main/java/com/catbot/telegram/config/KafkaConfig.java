package com.catbot.telegram.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    
    @Value("${kafka.topic.commands}")
    private String commandsTopic;
    
    @Value("${kafka.topic.events}")
    private String eventsTopic;
    
    @Bean
    public NewTopic commandsTopic() {
        return new NewTopic(commandsTopic, 1, (short) 1);
    }
    
    @Bean  
    public NewTopic eventsTopic() {
        return new NewTopic(eventsTopic, 1, (short) 1);
    }
} 