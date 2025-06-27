package com.catbot.telegram.transport.handlers;

import com.catbot.telegram.domain.BotCommands;
import com.catbot.telegram.service.UserStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// Обработчик команды /cancel
@Component
public class CancelCommandHandler implements CommandHandler {
    
    @Autowired
    private UserStateService stateService;
    
    private AbsSender bot;
    
    public void setBot(AbsSender bot) {
        this.bot = bot;
    }
    
    @Override
    public boolean canHandle(String command) {
        return BotCommands.CANCEL.equals(command);
    }
    
    @Override
    public void handle(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        Long chatId = update.getMessage().getChatId();
        
        // Очищаем состояние и данные пользователя
        stateService.clearUserData(userId);
        
        // Отправляем сообщение
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Операция отменена ❌\n\nИспользуй /start для начала");
        
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }
} 