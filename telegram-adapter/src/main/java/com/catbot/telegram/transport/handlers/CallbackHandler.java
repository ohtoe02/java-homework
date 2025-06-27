package com.catbot.telegram.transport.handlers;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

// Интерфейс для обработки callback от кнопок
public interface CallbackHandler {
    
    // Проверить может ли обработать callback
    boolean canHandle(String callbackData);
    
    // Обработать callback
    void handle(CallbackQuery callbackQuery);
} 