package dev.symo.finz.util;

import dev.symo.finz.FinZClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;


public class RenderUtils {
    public static Vec3d getCameraPos() {
        Camera camera = FinZClient.mc.getBlockEntityRenderDispatcher().camera;
        if (camera == null)
            return Vec3d.ZERO;

        return camera.getPos();
    }

    public static RegionPos getCameraRegion() {
        return RegionPos.of(getCameraBlockPos());
    }

    public static BlockPos getCameraBlockPos() {
        Camera camera = FinZClient.mc.getBlockEntityRenderDispatcher().camera;
        if (camera == null)
            return BlockPos.ORIGIN;

        return camera.getBlockPos();
    }
}
