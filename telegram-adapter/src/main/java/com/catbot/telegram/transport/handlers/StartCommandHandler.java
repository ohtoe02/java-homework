package com.catbot.telegram.transport.handlers;

import com.catbot.telegram.domain.BotCommands;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

// Обработчик команды /start
@Component
public class StartCommandHandler implements CommandHandler {
    
    private AbsSender bot;
    
    public void setBot(AbsSender bot) {
        this.bot = bot;
    }
    
    @Override
    public boolean canHandle(String command) {
        return BotCommands.START.equals(command);
    }
    
    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        
        // Создаем главное меню с кнопками
        InlineKeyboardMarkup keyboard = createMainMenu();
        
        // Отправляем приветственное сообщение
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Привет! 🐱 Я бот для котиков!\n\n" +
                       "Что хочешь сделать?");
        message.setReplyMarkup(keyboard);
        
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }
    
    private InlineKeyboardMarkup createMainMenu() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        
        // Кнопка "Добавить котика"
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton addCatBtn = new InlineKeyboardButton();
        addCatBtn.setText("➕ Добавить котика");
        addCatBtn.setCallbackData("add_cat");
        row1.add(addCatBtn);
        
        // Кнопка "Смотреть котиков"
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton viewCatsBtn = new InlineKeyboardButton();
        viewCatsBtn.setText("👀 Смотреть котиков");
        viewCatsBtn.setCallbackData("view_cats");
        row2.add(viewCatsBtn);
        
        // Кнопка "Мои котики"
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton myCatsBtn = new InlineKeyboardButton();
        myCatsBtn.setText("😸 Мои котики");
        myCatsBtn.setCallbackData("my_cats");
        row3.add(myCatsBtn);
        
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        
        markup.setKeyboard(keyboard);
        return markup;
    }
} 