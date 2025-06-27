package com.catbot.telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Главный класс приложения
@SpringBootApplication
public class TelegramAdapterApp {
    
    public static void main(String[] args) {
        SpringApplication.run(TelegramAdapterApp.class, args);
        System.out.println("Telegram adapter запущен!");
    }
} 