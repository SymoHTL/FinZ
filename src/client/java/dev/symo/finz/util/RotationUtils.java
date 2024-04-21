package dev.symo.finz.util;

import dev.symo.finz.FinZClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RotationUtils {
    public static Vec3d getClientLookVec(float partialTicks) {
        float yaw = FinZClient.mc.player.getYaw(partialTicks);
        float pitch = FinZClient.mc.player.getPitch(partialTicks);
        return new Rotation(yaw, pitch).toLookVec();
    }

    public static float limitAngleChange(float current, float intended) {
        float currentWrapped = MathHelper.wrapDegrees(current);
        float intendedWrapped = MathHelper.wrapDegrees(intended);

        float change = MathHelper.wrapDegrees(intendedWrapped - currentWrapped);

        return current + change;
    }
}
