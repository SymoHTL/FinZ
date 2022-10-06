package finz.events.impl;

import finz.events.Event;

public class Event2D extends Event {
    private float partialTicks;

    public Event2D() {
        this.partialTicks = 10f;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
