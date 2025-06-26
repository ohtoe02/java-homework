package com.catbot.telegram.config;

import com.catbot.telegram.transport.CatTelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {
    
    @Bean
    public TelegramBotsApi telegramBotsApi(CatTelegramBot bot) throws TelegramApiException {
        // Регистрируем бота
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot);
        System.out.println("Бот зарегистрирован!");
        return api;
    }
} 