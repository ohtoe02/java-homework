package com.catbot.cats.dto.response;

import com.catbot.cats.dto.CatDto;

// Ответ на добавление котика
public class AddCatResponse extends BaseResponse {
    
    private CatDto addedCat;
    
    public AddCatResponse() {
        super();
        setResponseType("ADD_CAT_RESPONSE");
    }
    
    public AddCatResponse(String eventId, Long userId, CatDto addedCat) {
        super(eventId, userId, "ADD_CAT_RESPONSE");
        this.addedCat = addedCat;
    }
    
    // Геттеры и сеттеры
    public CatDto getAddedCat() {
        return addedCat;
    }
    
    public void setAddedCat(CatDto addedCat) {
        this.addedCat = addedCat;
    }
} 