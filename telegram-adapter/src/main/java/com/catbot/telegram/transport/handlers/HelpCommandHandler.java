package com.catbot.telegram.transport.handlers;

import com.catbot.telegram.domain.BotCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// Обработчик команды /help
@Component
public class HelpCommandHandler implements CommandHandler {
    
    private AbsSender bot;
    
    public void setBot(AbsSender bot) {
        this.bot = bot;
    }
    
    @Override
    public boolean canHandle(String command) {
        return BotCommands.HELP.equals(command);
    }
    
    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        
        String helpText = "🐱 Бот для котиков - Справка\n\n" +
                         "Доступные команды:\n" +
                         "/start - Главное меню\n" +
                         "/help - Эта справка\n" +
                         "/cancel - Отменить текущую операцию\n\n" +
                         "Что умеет бот:\n" +
                         "• Добавлять котиков с фото и описанием\n" +
                         "• Показывать случайных котиков\n" +
                         "• Показывать твоих котиков\n" +
                         "• Ставить лайки котикам\n\n" +
                         "Используй кнопки в главном меню для навигации!";
        
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(helpText);
        
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки справки: " + e.getMessage());
        }
    }
} 