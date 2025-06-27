package com.catbot.cats.dto.response;

import com.catbot.cats.dto.CatDto;
import java.util.List;

// Ответ на получение котиков
public class GetCatsResponse extends BaseResponse {
    
    private List<CatDto> cats;
    private boolean onlyMyCats;
    
    public GetCatsResponse() {
        super();
        setResponseType("GET_CATS_RESPONSE");
    }
    
    public GetCatsResponse(String eventId, Long userId, List<CatDto> cats, boolean onlyMyCats) {
        super(eventId, userId, "GET_CATS_RESPONSE");
        this.cats = cats;
        this.onlyMyCats = onlyMyCats;
    }
    
    // Геттеры и сеттеры
    public List<CatDto> getCats() {
        return cats;
    }
    
    public void setCats(List<CatDto> cats) {
        this.cats = cats;
    }
    
    public boolean isOnlyMyCats() {
        return onlyMyCats;
    }
    
    public void setOnlyMyCats(boolean onlyMyCats) {
        this.onlyMyCats = onlyMyCats;
    }
} 