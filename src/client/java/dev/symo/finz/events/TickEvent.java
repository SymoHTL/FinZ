package dev.symo.finz.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public interface TickEvent {

    Event<TickEvent> TICK = EventFactory.createArrayBacked(TickEvent.class,
            (listeners) -> () -> {
                for (TickEvent listener : listeners) {
                    ActionResult result = listener.onTick();
                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult onTick();
}
