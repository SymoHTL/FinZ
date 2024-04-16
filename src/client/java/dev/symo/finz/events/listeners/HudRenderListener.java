package dev.symo.finz.events.listeners;

import dev.symo.finz.events.impl.Event;
import dev.symo.finz.events.impl.Listener;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public interface HudRenderListener extends Listener {
    void onHudRender(DrawContext drawContext, float tickDelta);

    public static class HudRenderEvent extends Event<HudRenderListener> {
        private final DrawContext drawContext;
        private final float tickDelta;

        public HudRenderEvent(DrawContext drawContext, float tickDelta) {
            this.drawContext = drawContext;
            this.tickDelta = tickDelta;
        }

        @Override
        public void fire(ArrayList<HudRenderListener> listeners) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            for (HudRenderListener listener : listeners)
                listener.onHudRender(drawContext, tickDelta);

            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        @Override
        public Class<HudRenderListener> getListenerType() {
            return HudRenderListener.class;
        }
    }
}