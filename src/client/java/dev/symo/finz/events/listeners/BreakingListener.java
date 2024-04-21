package dev.symo.finz.events.listeners;

import dev.symo.finz.events.impl.Event;
import dev.symo.finz.events.impl.Listener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

public interface BreakingListener extends Listener {
    public void onBlockBreakingProgress(BreakingProgressEvent event);

    public static class BreakingProgressEvent
            extends Event<BreakingListener> {
        private final BlockPos blockPos;
        private final Direction direction;

        public BreakingProgressEvent(BlockPos blockPos,
                                     Direction direction) {
            this.blockPos = blockPos;
            this.direction = direction;
        }

        @Override
        public void fire(ArrayList<BreakingListener> listeners) {
            for (BreakingListener listener : listeners)
                listener.onBlockBreakingProgress(this);
        }

        @Override
        public Class<BreakingListener> getListenerType() {
            return BreakingListener.class;
        }

        public BlockPos getBlockPos() {
            return blockPos;
        }

        public Direction getDirection() {
            return direction;
        }
    }
}
