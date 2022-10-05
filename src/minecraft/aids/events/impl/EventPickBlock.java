package aids.events.impl;

import aids.events.Event;
import net.minecraft.util.BlockPos;

public class EventPickBlock extends Event {
    public BlockPos blockPos;

    public EventPickBlock(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
