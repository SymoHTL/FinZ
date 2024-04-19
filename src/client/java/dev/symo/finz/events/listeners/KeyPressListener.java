package dev.symo.finz.events.listeners;

import dev.symo.finz.events.impl.Event;
import dev.symo.finz.events.impl.Listener;

import java.util.ArrayList;

public interface KeyPressListener extends Listener {
    void onKeyPress(KeyPressEvent event);

    class KeyPressEvent extends Event<KeyPressListener> {
        private final int keyCode;
        private final int scanCode;
        private final int action;

        public KeyPressEvent(int keyCode, int scanCode, int action) {
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.action = action;
        }

        @Override
        public void fire(ArrayList<KeyPressListener> listeners) {
            for (KeyPressListener listener : listeners)
                listener.onKeyPress(this);
        }

        @Override
        public Class<KeyPressListener> getListenerType() {
            return KeyPressListener.class;
        }

        public int getKeyCode() {
            return keyCode;
        }

        public int getScanCode() {
            return scanCode;
        }

        public int getAction() {
            return action;
        }
    }
}
