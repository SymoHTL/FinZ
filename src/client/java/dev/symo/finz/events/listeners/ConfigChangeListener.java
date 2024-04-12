package dev.symo.finz.events.listeners;


import dev.symo.finz.events.impl.Event;
import dev.symo.finz.events.impl.Listener;

import java.util.ArrayList;

public interface ConfigChangeListener extends Listener {
    public void onConfigChange();

    public static class ConfigChangeEvent extends Event<ConfigChangeListener> {
        @Override
        public void fire(ArrayList<ConfigChangeListener> listeners) {
            for (ConfigChangeListener listener : listeners)
                listener.onConfigChange();
        }

        @Override
        public Class<ConfigChangeListener> getListenerType() {
            return ConfigChangeListener.class;
        }
    }
}
