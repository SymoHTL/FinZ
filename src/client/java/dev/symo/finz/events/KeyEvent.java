package dev.symo.finz.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface KeyEvent {

    Event<KeyEvent> KEY_EVENT = EventFactory.createArrayBacked(KeyEvent.class,
            (listeners) -> () -> {
                for (KeyEvent listener : listeners) {
                    ActionResult result = listener.onKey();
                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult onKey();
}
