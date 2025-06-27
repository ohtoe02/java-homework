package com.catbot.telegram.domain;

// Класс с командами бота
public class BotCommands {
    
    public static final String START = "/start";
    public static final String HELP = "/help";
    public static final String ADD_CAT = "/addcat";
    public static final String MY_CATS = "/mycats";
    public static final String RANDOM_CAT = "/randomcat";
    public static final String CANCEL = "/cancel";
    
    // Приватный конструктор чтобы нельзя было создать экземпляр
    private BotCommands() {
    }
} 