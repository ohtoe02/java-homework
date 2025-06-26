package com.catbot.telegram.transport.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

// Интерфейс для обработки команд
public interface CommandHandler {
    
    // Может ли обработчик обработать эту команду
    boolean canHandle(String command);
    
    // Обработать команду
    void handle(Update update);
} 