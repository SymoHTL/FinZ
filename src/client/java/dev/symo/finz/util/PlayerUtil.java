package dev.symo.finz.util;

import dev.symo.finz.FinZClient;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil {

    public static BlockPos getAccurateBlockPos() {
        if (FinZClient.mc.getCameraEntity() == null) return new BlockPos(0, 0, 0);
        return FinZClient.mc.getCameraEntity().getBlockPos();
    }

    public static BlockPos getBlockPosBelow() {
        BlockPos pos = getAccurateBlockPos();
        return new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
    }
}
