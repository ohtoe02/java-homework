package com.catbot.telegram.transport.handlers;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackHandler {
    
    boolean canHandle(String callbackData);
    
    void handle(CallbackQuery callbackQuery);
} 