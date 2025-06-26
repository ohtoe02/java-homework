package com.catbot.telegram.service;

public interface TelegramResponseService {
    
    void handleAddCatResponse(String responseJson);
    
    void handleGetCatsResponse(String responseJson);
    
    void handleLikeCatResponse(String responseJson);
} 