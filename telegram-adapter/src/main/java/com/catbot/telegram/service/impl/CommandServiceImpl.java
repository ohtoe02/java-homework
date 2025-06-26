package com.catbot.telegram.service.impl;

import com.catbot.telegram.domain.UserState;
import com.catbot.telegram.domain.events.AddCatEvent;
import com.catbot.telegram.domain.events.GetCatsEvent;
import com.catbot.telegram.service.CommandService;
import com.catbot.telegram.service.KafkaProducer;
import com.catbot.telegram.service.UserStateService;
import com.catbot.telegram.transport.handlers.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.UUID;

@Service
public class CommandServiceImpl implements CommandService {
    
    @Autowired
    private UserStateService stateService;
    
    @Autowired
    private KafkaProducer kafkaProducer;
    
    @Autowired
    private List<CommandHandler> commandHandlers;
    
    @Value("${kafka.topic.commands}")
    private String commandsTopic;
    
    private AbsSender bot;
    
    public void setBot(AbsSender bot) {
        this.bot = bot;
    }
    
    @Override
    public void processCommand(Update update) {
        String command = update.getMessage().getText();
        
        for (CommandHandler handler : commandHandlers) {
            if (handler.canHandle(command)) {
                handler.handle(update);
                return;
            }
        }
        
        sendMessage(update.getMessage().getChatId(), 
                   "Неизвестная команда. Используй /start для начала");
    }
    
    @Override
    public void processMessage(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        Long chatId = update.getMessage().getChatId();
        String state = stateService.getUserState(userId);
        
        System.out.println("Обработка сообщения. Состояние: " + state);
        
        switch (state) {
            case UserState.WAITING_CAT_NAME:
                handleCatName(userId, chatId, update.getMessage().getText());
                break;
            case UserState.WAITING_CAT_PHOTO:
                handleCatPhoto(userId, chatId, update);
                break;
            case UserState.WAITING_CAT_DESCRIPTION:
                handleCatDescription(userId, chatId, update.getMessage().getText());
                break;
            default:
                sendMessage(chatId, "Я не понимаю. Используй /start");
        }
    }
    
    private void handleCatName(Long userId, Long chatId, String catName) {
        stateService.saveUserData(userId, "catName", catName);
        stateService.setUserState(userId, UserState.WAITING_CAT_PHOTO);
        
        sendMessage(chatId, "Хорошее имя! 😊\n\nТеперь отправь фото котика 📸");
    }
    
    private void handleCatPhoto(Long userId, Long chatId, Update update) {
        if (!update.getMessage().hasPhoto()) {
            sendMessage(chatId, "Пожалуйста, отправь фото котика 📸");
            return;
        }
        
        String photoId = update.getMessage().getPhoto()
            .get(update.getMessage().getPhoto().size() - 1)
            .getFileId();
        
        stateService.saveUserData(userId, "photoId", photoId);
        stateService.setUserState(userId, UserState.WAITING_CAT_DESCRIPTION);
        
        sendMessage(chatId, "Отличное фото! 😍\n\nТеперь напиши описание котика");
    }
    
    private void handleCatDescription(Long userId, Long chatId, String description) {
        String catName = (String) stateService.getUserData(userId, "catName");
        String photoId = (String) stateService.getUserData(userId, "photoId");
        
        AddCatEvent event = new AddCatEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setUserId(userId);
        event.setCatName(catName);
        event.setPhotoUrl(photoId);
        event.setDescription(description);
        
        kafkaProducer.sendCommand(event);
        
        stateService.clearUserData(userId);
        
        sendMessage(chatId, "Котик добавлен! 🎉\n\nИспользуй /start для главного меню");
    }
    
    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки: " + e.getMessage());
        }
    }
} 