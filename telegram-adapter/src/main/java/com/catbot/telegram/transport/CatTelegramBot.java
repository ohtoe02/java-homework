package com.catbot.telegram.transport;

import com.catbot.telegram.domain.BotCommands;
import com.catbot.telegram.service.CommandService;
import com.catbot.telegram.service.impl.CommandServiceImpl;
import com.catbot.telegram.transport.handlers.CallbackHandler;
import com.catbot.telegram.transport.handlers.CommandHandler;
import com.catbot.telegram.transport.handlers.MainMenuCallbackHandler;
import com.catbot.telegram.transport.handlers.StartCommandHandler;
import com.catbot.telegram.transport.handlers.CancelCommandHandler;
import com.catbot.telegram.transport.handlers.HelpCommandHandler;
import com.catbot.telegram.service.impl.TelegramResponseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.List;

// Основной класс бота
@Component
public class CatTelegramBot extends TelegramLongPollingBot {
    
    @Value("${telegram.bot.token}")
    private String token;
    
    @Value("${telegram.bot.username}")
    private String username;
    
    @Autowired
    private CommandService commandService;
    
    @Autowired
    private List<CommandHandler> commandHandlers;
    
    @Autowired
    private List<CallbackHandler> callbackHandlers;
    
    @Autowired
    private TelegramResponseServiceImpl telegramResponseService;
    
    // Инициализация после создания бина
    @PostConstruct
    public void init() {
        // Устанавливаем бота во все сервисы
        if (commandService instanceof CommandServiceImpl) {
            ((CommandServiceImpl) commandService).setBot(this);
        }
        
        // Устанавливаем бота в обработчики
        for (CommandHandler handler : commandHandlers) {
            if (handler instanceof StartCommandHandler) {
                ((StartCommandHandler) handler).setBot(this);
            } else if (handler instanceof CancelCommandHandler) {
                ((CancelCommandHandler) handler).setBot(this);
            } else if (handler instanceof HelpCommandHandler) {
                ((HelpCommandHandler) handler).setBot(this);
            }
        }
        
        for (CallbackHandler handler : callbackHandlers) {
            if (handler instanceof MainMenuCallbackHandler) {
                ((MainMenuCallbackHandler) handler).setBot(this);
            }
        }
        
        // Устанавливаем бота в сервис ответов
        telegramResponseService.setBot(this);
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Получено обновление: " + update.getUpdateId());
        
        try {
            if (update.hasMessage()) {
                handleMessage(update);
            } else if (update.hasCallbackQuery()) {
                handleCallback(update);
            }
        } catch (Exception e) {
            System.err.println("Ошибка обработки обновления: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void handleMessage(Update update) {
        if (update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            
            // Проверяем, это команда или обычное сообщение
            if (text.startsWith("/")) {
                commandService.processCommand(update);
            } else {
                commandService.processMessage(update);
            }
        } else if (update.getMessage().hasPhoto()) {
            // Обрабатываем фото
            commandService.processMessage(update);
        }
    }
    
    private void handleCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        
        // Ищем подходящий обработчик
        for (CallbackHandler handler : callbackHandlers) {
            if (handler.canHandle(callbackData)) {
                handler.handle(update.getCallbackQuery());
                return;
            }
        }
        
        System.out.println("Не найден обработчик для callback: " + callbackData);
    }
    
    @Override
    public String getBotUsername() {
        return username;
    }
    
    @Override
    public String getBotToken() {
        return token;
    }
} 