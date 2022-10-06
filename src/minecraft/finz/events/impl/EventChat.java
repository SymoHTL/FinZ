package finz.events.impl;

import finz.events.Event;

public class EventChat extends Event {
    private String message;

    public EventChat(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
