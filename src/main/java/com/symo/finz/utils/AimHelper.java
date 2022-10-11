package com.symo.finz.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class AimHelper {

    public static float[] getYawAndPitchToLookAt(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) -  Minecraft.getMinecraft().thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() -  Minecraft.getMinecraft().thePlayer.posY +  Minecraft.getMinecraft().thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) -  Minecraft.getMinecraft().thePlayer.posZ,
                distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
        double v = Math.toDegrees(Math.atan(deltaZ / deltaX));
        if (deltaX < 0 && deltaZ < 0)
            yaw = (float) (90 + v);
        else if (deltaX > 0 && deltaZ < 0)
            yaw = (float) (-90 + v);
        return new float[]{yaw, pitch};
    }
}
