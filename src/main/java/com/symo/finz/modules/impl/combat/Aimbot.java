package com.symo.finz.modules.impl.combat;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.AimHelper;
import com.symo.finz.utils.extension.PlayerExtension;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;

public class Aimbot extends Module {

    public Aimbot() {
        super("Aimbot", Keyboard.KEY_U, "FinZ - Combat");
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.AimBotEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.AimBotEnabled = enabled;
    }

    public void onUpdate() {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (mc.thePlayer.isDead)
            return;
        EntityLivingBase entity = PlayerExtension.getClosetLivingEntity();

        if (entity == null)
            return;
        if (!PlayerExtension.canSeeEyesMidOrFeet(entity))
            return;
        if (entity.getDistanceToEntity(mc.thePlayer) > FinZ.configFile.AimBotReach)
            return;

        float[] rotations = AimHelper.getYawAndPitchToLookAt(entity);
        mc.thePlayer.rotationYaw = rotations[0];
        mc.thePlayer.rotationPitch = rotations[1];

    }


}
