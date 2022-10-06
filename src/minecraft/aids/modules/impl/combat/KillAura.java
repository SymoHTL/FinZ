package aids.modules.impl.combat;

import aids.events.impl.EventMotion;
import aids.modules.Category;
import aids.modules.Module;
import aids.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {
    public Timer timer = new Timer();

    public KillAura() {
        super("KillAura", "Attacks nearby entities", Category.COMBAT);
        setKey(Keyboard.KEY_NONE);
    }

    public void onMotion(EventMotion event) {
        if (mc.thePlayer.isDead) {
            return;
        }

        List<Entity> entities = mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
        List<EntityLivingBase> livingBases = entities.stream().map(entity -> (EntityLivingBase) entity).collect(Collectors.toList());
        livingBases = livingBases.stream().filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < 4 && entity != mc.thePlayer && !entity.isDead && entity.getHealth() > 0).collect(Collectors.toList());
        livingBases.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer)));
        //// Only players TODO
        //// entities = entities.stream().filter(EntityPlayer.class::isInstance).collect(Collectors.toList());

        if (!livingBases.isEmpty()) {
            if (timer.hasTimeElapsed(1000 / 10, true)) {
                float[] rotations = getRotations(livingBases.get(0));
                mc.thePlayer.swingItem();
                event.setYaw(rotations[0]);
                event.setPitch(rotations[1]);
                mc.getNetHandler().addToSendQueue(new C02PacketUseEntity(livingBases.get(0), C02PacketUseEntity.Action.ATTACK));
            }
        }
    }


    public float[] getRotations(Entity e) {
        double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
                deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
                deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
                distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        float yaw = (float) Math.toDegrees(-Math.atan(deltaX / deltaZ)),
                pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
        if (deltaX < 0 && deltaZ < 0) {
            yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        } else if (deltaX > 0 && deltaZ < 0) {
            yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        return new float[]{
                yaw, pitch
        };
    }
}
