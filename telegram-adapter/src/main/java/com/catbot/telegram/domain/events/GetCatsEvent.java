package com.catbot.telegram.domain.events;

// Событие запроса котиков
public class GetCatsEvent extends BaseEvent {
    
    private boolean onlyMyCats; // только мои котики или все
    
    public GetCatsEvent() {
        super();
        setEventType("GET_CATS");
    }
    
    public boolean isOnlyMyCats() {
        return onlyMyCats;
    }
    
    public void setOnlyMyCats(boolean onlyMyCats) {
        this.onlyMyCats = onlyMyCats;
    }
} 