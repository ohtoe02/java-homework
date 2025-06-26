package com.catbot.telegram.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandService {
    
    void processCommand(Update update);
    
    void processMessage(Update update);
} 