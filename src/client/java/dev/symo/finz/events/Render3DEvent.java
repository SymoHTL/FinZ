package dev.symo.finz.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.ActionResult;

public interface Render3DEvent {
    Event<Render3DEvent> EVENT3D = EventFactory.createArrayBacked(Render3DEvent.class,
            (listeners) -> (MatrixStack matrixStack, float partialTicks) -> {
                for (Render3DEvent listener : listeners) {
                    ActionResult result = listener.on3DRender(matrixStack, partialTicks);
                    if (result != ActionResult.PASS)
                        return result;
                }
                return ActionResult.PASS;
            });

    ActionResult on3DRender(MatrixStack matrixStack, float partialTicks);
}
