package finz.events.impl;

import finz.events.Event;

public class EventKey extends Event {
    private int key;

    public EventKey(int key) {
        super();
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
