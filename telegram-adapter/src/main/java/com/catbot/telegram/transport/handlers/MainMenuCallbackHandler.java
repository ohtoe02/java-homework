package com.catbot.telegram.transport.handlers;

import com.catbot.telegram.domain.UserState;
import com.catbot.telegram.domain.events.GetCatsEvent;
import com.catbot.telegram.domain.events.LikeCatEvent;
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
               "my_cats".equals(callbackData) ||
               callbackData.startsWith("like_") ||
               callbackData.startsWith("dislike_");
    }
    
    @Override
    public void handle(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        String data = callbackQuery.getData();
        
        if ("add_cat".equals(data)) {
            handleAddCat(userId, callbackQuery);
        } else if ("view_cats".equals(data)) {
            handleViewCats(userId, callbackQuery);
        } else if ("my_cats".equals(data)) {
            handleMyCats(userId, callbackQuery);
        } else if (data.startsWith("like_")) {
            handleLike(userId, callbackQuery, true);
        } else if (data.startsWith("dislike_")) {
            handleLike(userId, callbackQuery, false);
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
    
    private void handleLike(Long userId, CallbackQuery callback, boolean isLike) {
        String data = callback.getData();
        // Извлекаем ID котика из callback data (like_123 или dislike_123)
        String prefix = isLike ? "like_" : "dislike_";
        String catId = data.substring(prefix.length());
        
        System.out.println("Пользователь " + userId + " ставит " + 
                          (isLike ? "лайк" : "дизлайк") + " котику " + catId);
        
        // Создаем событие лайка
        LikeCatEvent event = new LikeCatEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setUserId(userId);
        event.setCatId(catId);
        event.setLike(isLike);
        
        // Отправляем в Kafka
        kafkaProducer.sendCommand(event);
        
        // Отвечаем на callback query чтобы убрать "часики"
        try {
            org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery answer = 
                new org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery();
            answer.setCallbackQueryId(callback.getId());
            answer.setText(isLike ? "👍 Лайк!" : "👎 Дизлайк!");
            answer.setShowAlert(false);
            
            bot.execute(answer);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка ответа на callback: " + e.getMessage());
        }
    }
} 