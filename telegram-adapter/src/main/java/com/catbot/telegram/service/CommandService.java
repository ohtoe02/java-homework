package com.catbot.telegram.service;

import org.telegram.telegrambots.meta.api.objects.Update;

// Сервис обработки команд
public interface CommandService {
    
    // Обработать команду
    void processCommand(Update update);
    
    // Обработать обычное сообщение
    void processMessage(Update update);
} 