package com.symo.finz.utils;

import com.symo.finz.FinZ;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class AimHelper {

    public static float[] getYawAndPitchToLookAt(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - FinZ.mc.thePlayer.posX,
                deltaY = e.posY - (FinZ.mc.thePlayer.isSneaking() ? 3.08 : 3.24) + e.getEyeHeight() - FinZ.mc.thePlayer.posY + FinZ.mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - FinZ.mc.thePlayer.posZ,
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

    public static float[] getYawAndPitchToLookAt(double[] pos) {
        double deltaX = pos[0] - FinZ.mc.thePlayer.posX,
                deltaY = pos[1] - (FinZ.mc.thePlayer.isSneaking() ? 3.08 : 3.24) - Minecraft.getMinecraft().thePlayer.posY + FinZ.mc.thePlayer.getEyeHeight(),
                deltaZ = pos[2] - FinZ.mc.thePlayer.posZ,
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
