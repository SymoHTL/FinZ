package dev.symo.finz.events.impl;

public abstract class CancellableEvent<T extends Listener> extends Event<T> {
    private boolean cancelled = false;

    public void cancel() {
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
