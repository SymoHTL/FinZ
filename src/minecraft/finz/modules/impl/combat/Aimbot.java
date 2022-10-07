package finz.modules.impl.combat;

import finz.events.impl.EventUpdate;
import finz.modules.Category;
import finz.modules.Module;
import finz.util.AimHelper;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;

public class Aimbot extends Module {

    public Aimbot() {
        super("Aimbot", "Aims at things", Category.COMBAT);
        setKey(Keyboard.KEY_U);
    }

    @Override
    public void onUpdate(EventUpdate e) {
        if (mc.thePlayer.isDead)
            return;

        EntityLivingBase entity = mc.thePlayer.getClosetLivingEntity();

        if (entity == null)
            return;
        if (entity.getDistanceToEntity(mc.thePlayer) > 4)
            return;

        float[] rotations = AimHelper.getYawAndPitchToLookAt(entity);
        mc.thePlayer.rotationYaw = rotations[0];
        mc.thePlayer.rotationPitch = rotations[1];
    }


}
