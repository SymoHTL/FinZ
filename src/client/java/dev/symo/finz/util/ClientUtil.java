package dev.symo.finz.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ClientUtil {

    private static final MinecraftClient mc;

    static {
        mc = MinecraftClient.getInstance();
    }


    public static Vec3d getCameraPos()
    {
        Camera camera = mc.getBlockEntityRenderDispatcher().camera;
        if(camera == null)
            return Vec3d.ZERO;

        return camera.getPos();
    }

    public static BlockPos getCameraBlockPos()    {
        Camera camera = mc.getBlockEntityRenderDispatcher().camera;
        if(camera == null)
            return BlockPos.ORIGIN;

        return camera.getBlockPos();
    }
}
