package com.symo.finz.modules.impl.combat;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.AimHelper;
import com.symo.finz.utils.extension.PlayerExtension;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Aimbot extends Module {

    public Aimbot() {
        super("Aimbot", Keyboard.KEY_U, "combat");
    }

    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.thePlayer.isDead)
            return;
        EntityLivingBase entity = PlayerExtension.getClosetLivingEntity();

        if (entity == null)
            return;
        if (entity.getDistanceToEntity(mc.thePlayer) > 4)
            return;
        if (!PlayerExtension.canSeeEyesMidOrFeet(entity))
            return;

        float[] rotations = AimHelper.getYawAndPitchToLookAt(entity);
        mc.thePlayer.rotationYaw = rotations[0];
        mc.thePlayer.rotationPitch = rotations[1];
    }


}
