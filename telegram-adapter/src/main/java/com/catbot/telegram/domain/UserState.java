package com.catbot.telegram.domain;

// Состояния пользователя в боте
public class UserState {
    
    public static final String IDLE = "IDLE";
    public static final String WAITING_CAT_NAME = "WAITING_CAT_NAME";
    public static final String WAITING_CAT_PHOTO = "WAITING_CAT_PHOTO";
    public static final String WAITING_CAT_DESCRIPTION = "WAITING_CAT_DESCRIPTION";
    
    private UserState() {
    }
} 