package aids.modules.impl.combat;

import aids.events.impl.EventUpdate;
import aids.modules.Category;
import aids.modules.Module;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;

public class Aimbot extends Module {

    public Aimbot() {
        super("Aimbot", "Aims at players", Category.COMBAT);
        setKey(Keyboard.KEY_U);
    }

    @Override
    public void onUpdate(EventUpdate e) {
        for (Object o : mc.theWorld.getLoadedEntityList()) {
            if (o instanceof EntityLivingBase && o != mc.thePlayer && ((EntityLivingBase) o).getHealth() > 0) {
                EntityLivingBase entity = (EntityLivingBase) o;
                double distance = mc.thePlayer.getDistanceToEntity(entity);
                if (distance <= 4) {
                    double x = entity.posX - mc.thePlayer.posX;
                    double y = entity.posY-0.5 - mc.thePlayer.posY;
                    double z = entity.posZ - mc.thePlayer.posZ;

                    double angle = Math.toDegrees(Math.atan2(z, x)) - 90;

                    mc.thePlayer.rotationYaw = (float) angle;
                    mc.thePlayer.rotationPitch = (float) -Math.toDegrees(Math.atan2(y, distance));
                }
            }
        }

    }


}
