package dev.symo.finz.events.listeners;


import dev.symo.finz.events.impl.Event;
import dev.symo.finz.events.impl.Listener;

import java.util.ArrayList;

public interface TickListener extends Listener {
    void onTick();

    public static class UpdateEvent extends Event<TickListener>
    {
        public static final UpdateEvent INSTANCE = new UpdateEvent();

        @Override
        public void fire(ArrayList<TickListener> listeners)
        {
            for(TickListener listener : listeners)
                listener.onTick();
        }

        @Override
        public Class<TickListener> getListenerType()
        {
            return TickListener.class;
        }
    }
}
