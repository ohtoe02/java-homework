package com.catbot.telegram.domain.events;

public class GetCatsEvent extends BaseEvent {
    
    private boolean onlyMyCats;
    
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