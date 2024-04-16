package dev.symo.finz.events.listeners;

import dev.symo.finz.events.impl.Event;
import dev.symo.finz.events.impl.Listener;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public interface WorldRenderListener extends Listener {
    void onWorldRender(MatrixStack matrixStack, float partialTicks);

    public static class RenderEvent extends Event<WorldRenderListener> {
        private final MatrixStack matrixStack;
        private final float partialTicks;

        public RenderEvent(MatrixStack matrixStack, float partialTicks) {
            this.matrixStack = matrixStack;
            this.partialTicks = partialTicks;
        }

        @Override
        public void fire(ArrayList<WorldRenderListener> listeners) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            for (WorldRenderListener listener : listeners)
                listener.onWorldRender(matrixStack, partialTicks);

            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        @Override
        public Class<WorldRenderListener> getListenerType() {
            return WorldRenderListener.class;
        }
    }
}
