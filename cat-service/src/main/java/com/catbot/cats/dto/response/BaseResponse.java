package com.catbot.cats.dto.response;

import java.time.LocalDateTime;

// Базовый класс для ответов от cat-service
public class BaseResponse {
    
    private String eventId; // ID исходного события
    private Long userId;
    private LocalDateTime timestamp;
    private String responseType;
    private boolean success;
    private String errorMessage;
    
    public BaseResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    public BaseResponse(String eventId, Long userId, String responseType) {
        this();
        this.eventId = eventId;
        this.userId = userId;
        this.responseType = responseType;
        this.success = true;
    }
    
    // Геттеры и сеттеры
    public String getEventId() {
        return eventId;
    }
    
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getResponseType() {
        return responseType;
    }
    
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
} 