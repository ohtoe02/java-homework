package com.catbot.telegram.transport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

// Основной класс бота
@Component
public class CatTelegramBot extends TelegramLongPollingBot {
    
    @Value("${telegram.bot.token}")
    private String token;
    
    @Value("${telegram.bot.username}")
    private String username;
    
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Получено сообщение: " + update);
        
        if (update.hasMessage()) {
            handleMessage(update);
        }
        
        if (update.hasCallbackQuery()) {
            handleCallback(update);
        }
    }
    
    private void handleMessage(Update update) {
        System.out.println("Обработка сообщения...");
    }
    
    private void handleCallback(Update update) {
        System.out.println("Обработка callback...");
    }
    
    @Override
    public String getBotUsername() {
        return username;
    }
    
    @Override
    public String getBotToken() {
        return token;
    }
} 