package com.catbot.cats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Главный класс сервиса котиков
@SpringBootApplication
public class CatServiceApp {
    
    public static void main(String[] args) {
        SpringApplication.run(CatServiceApp.class, args);
        System.out.println("Cat service запущен!");
    }
} 