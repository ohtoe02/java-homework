package com.catbot.telegram.service.impl;

import com.catbot.telegram.service.TelegramResponseService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

// Реализация сервиса обработки ответов
@Service
public class TelegramResponseServiceImpl implements TelegramResponseService {
    
    private ObjectMapper objectMapper = new ObjectMapper();
    private AbsSender bot; // Устанавливается через setBot
    
    public void setBot(AbsSender bot) {
        this.bot = bot;
    }
    
    @Override
    public void handleAddCatResponse(String responseJson) {
        try {
            JsonNode response = objectMapper.readTree(responseJson);
            Long userId = response.get("userId").asLong();
            boolean success = response.get("success").asBoolean();
            
            if (success) {
                // Успешное добавление
                JsonNode addedCat = response.get("addedCat");
                String catName = addedCat.get("name").asText();
                
                SendMessage message = new SendMessage();
                message.setChatId(userId.toString());
                message.setText("🎉 Котик \"" + catName + "\" успешно добавлен!\n\n" +
                               "Теперь другие пользователи смогут его видеть и ставить лайки!");
                
                bot.execute(message);
                System.out.println("Отправлено подтверждение добавления котика пользователю: " + userId);
                
            } else {
                // Ошибка добавления
                String errorMessage = response.get("errorMessage").asText();
                
                SendMessage message = new SendMessage();
                message.setChatId(userId.toString());
                message.setText("❌ Ошибка при добавлении котика:\n" + errorMessage + 
                               "\n\nПопробуй еще раз позже.");
                
                bot.execute(message);
                System.out.println("Отправлена ошибка добавления котика пользователю: " + userId);
            }
            
        } catch (Exception e) {
            System.err.println("Ошибка обработки ответа добавления котика: " + e.getMessage());
        }
    }
    
    @Override
    public void handleGetCatsResponse(String responseJson) {
        try {
            JsonNode response = objectMapper.readTree(responseJson);
            Long userId = response.get("userId").asLong();
            boolean success = response.get("success").asBoolean();
            
            if (success) {
                JsonNode catsArray = response.get("cats");
                boolean onlyMyCats = response.get("onlyMyCats").asBoolean();
                
                if (catsArray.size() == 0) {
                    // Нет котиков
                    String message = onlyMyCats ? 
                        "😿 У тебя пока нет котиков.\nИспользуй /start чтобы добавить первого!" :
                        "😿 Котиков пока нет в системе.\nБудь первым - добавь котика!";
                    
                    sendTextMessage(userId, message);
                } else {
                    // Отправляем котиков
                    for (JsonNode catNode : catsArray) {
                        sendCatMessage(userId, catNode);
                    }
                }
                
            } else {
                // Ошибка получения
                String errorMessage = response.get("errorMessage").asText();
                sendTextMessage(userId, "❌ Ошибка получения котиков: " + errorMessage);
            }
            
        } catch (Exception e) {
            System.err.println("Ошибка обработки ответа получения котиков: " + e.getMessage());
        }
    }
    
    @Override
    public void handleLikeCatResponse(String responseJson) {
        try {
            JsonNode response = objectMapper.readTree(responseJson);
            Long userId = response.get("userId").asLong();
            boolean success = response.get("success").asBoolean();
            
            if (success) {
                boolean isLike = response.get("like").asBoolean();
                int newLikes = response.get("newLikes").asInt();
                int newDislikes = response.get("newDislikes").asInt();
                
                String message = isLike ? 
                    "👍 Лайк поставлен! Всего лайков: " + newLikes :
                    "👎 Дизлайк поставлен! Всего дизлайков: " + newDislikes;
                
                sendTextMessage(userId, message);
                
            } else {
                String errorMessage = response.get("errorMessage").asText();
                sendTextMessage(userId, "❌ Ошибка: " + errorMessage);
            }
            
        } catch (Exception e) {
            System.err.println("Ошибка обработки ответа лайка: " + e.getMessage());
        }
    }
    
    private void sendTextMessage(Long chatId, String text) {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(chatId.toString());
            message.setText(text);
            bot.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения: " + e.getMessage());
        }
    }
    
    private void sendCatMessage(Long chatId, JsonNode catNode) {
        try {
            String name = catNode.get("name").asText();
            String photoUrl = catNode.get("photoUrl").asText();
            String description = catNode.get("description").asText();
            int likes = catNode.get("likes").asInt();
            int dislikes = catNode.get("dislikes").asInt();
            Long catId = catNode.get("id").asLong();
            
            // Создаем кнопки лайк/дизлайк
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rows = new ArrayList<>();
            
            List<InlineKeyboardButton> row = new ArrayList<>();
            
            InlineKeyboardButton likeBtn = new InlineKeyboardButton();
            likeBtn.setText("👍 " + likes);
            likeBtn.setCallbackData("like_" + catId);
            row.add(likeBtn);
            
            InlineKeyboardButton dislikeBtn = new InlineKeyboardButton();
            dislikeBtn.setText("👎 " + dislikes);
            dislikeBtn.setCallbackData("dislike_" + catId);
            row.add(dislikeBtn);
            
            rows.add(row);
            keyboard.setKeyboard(rows);
            
            // Отправляем фото с описанием
            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId.toString());
            photo.setPhoto(new InputFile(photoUrl));
            photo.setCaption("🐱 " + name + "\n\n" + description + 
                           "\n\n👍 " + likes + " | 👎 " + dislikes);
            photo.setReplyMarkup(keyboard);
            
            bot.execute(photo);
            
        } catch (Exception e) {
            System.err.println("Ошибка отправки котика: " + e.getMessage());
            // Если фото не отправилось, отправляем текстом
            try {
                String name = catNode.get("name").asText();
                String description = catNode.get("description").asText();
                int likes = catNode.get("likes").asInt();
                int dislikes = catNode.get("dislikes").asInt();
                
                sendTextMessage(chatId, "🐱 " + name + "\n\n" + description + 
                               "\n\n👍 " + likes + " | 👎 " + dislikes);
            } catch (Exception ex) {
                System.err.println("Ошибка отправки текста котика: " + ex.getMessage());
            }
        }
    }
} 