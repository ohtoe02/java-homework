package com.catbot.telegram.transport.handlers;

import com.catbot.telegram.domain.UserState;
import com.catbot.telegram.domain.events.GetCatsEvent;
import com.catbot.telegram.service.KafkaProducer;
import com.catbot.telegram.service.UserStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// Обработчик кнопок главного меню
@Component  
public class MainMenuCallbackHandler implements CallbackHandler {
    
    @Autowired
    private UserStateService stateService;
    
    @Autowired
    private KafkaProducer kafkaProducer;
    
    private AbsSender bot;
    
    public void setBot(AbsSender bot) {
        this.bot = bot;
    }
    
    @Override
    public boolean canHandle(String callbackData) {
        return "add_cat".equals(callbackData) || 
               "view_cats".equals(callbackData) || 
               "my_cats".equals(callbackData);
    }
    
    @Override
    public void handle(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        String data = callbackQuery.getData();
        
        switch (data) {
            case "add_cat":
                handleAddCat(userId, callbackQuery);
                break;
            case "view_cats":
                handleViewCats(userId, callbackQuery);
                break;
            case "my_cats":
                handleMyCats(userId, callbackQuery);
                break;
        }
    }
    
    private void handleAddCat(Long userId, CallbackQuery callback) {
        // Меняем состояние пользователя
        stateService.setUserState(userId, UserState.WAITING_CAT_NAME);
        
        // Редактируем сообщение
        EditMessageText edit = new EditMessageText();
        edit.setChatId(callback.getMessage().getChatId().toString());
        edit.setMessageId(callback.getMessage().getMessageId());
        edit.setText("Отлично! Давай добавим котика 🐱\n\n" +
                    "Напиши имя котика:");
        
        try {
            bot.execute(edit);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка редактирования сообщения: " + e.getMessage());
        }
    }
    
    private void handleViewCats(Long userId, CallbackQuery callback) {
        // Отправить событие в Kafka для получения случайного котика
        SendMessage message = new SendMessage();
        message.setChatId(callback.getMessage().getChatId().toString());
        message.setText("Загружаю котиков... 🐾");
        
        try {
            bot.execute(message);
            
            // Создаем событие для получения котиков
            GetCatsEvent event = new GetCatsEvent();
            event.setEventId(java.util.UUID.randomUUID().toString());
            event.setUserId(userId);
            event.setOnlyMyCats(false);
            
            // Отправляем в Kafka
            kafkaProducer.sendCommand(event);
            System.out.println("Отправлен запрос на получение котиков");
        } catch (TelegramApiException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
    
    private void handleMyCats(Long userId, CallbackQuery callback) {
        // Отправить событие в Kafka для получения котиков пользователя
        SendMessage message = new SendMessage();
        message.setChatId(callback.getMessage().getChatId().toString());
        message.setText("Загружаю твоих котиков... 😸");
        
        try {
            bot.execute(message);
            
            // Создаем событие для получения моих котиков
            GetCatsEvent event = new GetCatsEvent();
            event.setEventId(java.util.UUID.randomUUID().toString());
            event.setUserId(userId);
            event.setOnlyMyCats(true);
            
            // Отправляем в Kafka
            kafkaProducer.sendCommand(event);
            System.out.println("Отправлен запрос на получение моих котиков");
        } catch (TelegramApiException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
} 