package dev.symo.finz.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface ConfigChangeEvent {
    Event<ConfigChangeEvent> CHANGE = EventFactory.createArrayBacked(ConfigChangeEvent.class,
            (listeners) -> () -> {
                for (ConfigChangeEvent listener : listeners) {
                    ActionResult result = listener.onChange();
                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult onChange();
}
