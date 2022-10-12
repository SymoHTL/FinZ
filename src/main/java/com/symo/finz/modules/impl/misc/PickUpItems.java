package com.symo.finz.modules.impl.misc;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.extension.PlayerInventoryExtension;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.input.Keyboard;

public class PickUpItems extends Module {
    public PickUpItems() {
        super("PickUpItems", Keyboard.KEY_M, "FinZ - Misc");
    }

    public void onUpdate() {
            // get dropped items
            if (PlayerInventoryExtension.isFull()) return;
            for (Object o : mc.theWorld.getLoadedEntityList()) {
                if (o instanceof EntityItem) {
                    EntityItem item = (EntityItem) o;
                    if (item.posY < mc.thePlayer.posY) continue;
                    // get distance to item
                    double distance = mc.thePlayer.getDistanceToEntity(item);
                    // if the distance is less than 4 blocks
                    if (distance <= 4) {
                        // get the item's position
                        double x = item.posX - mc.thePlayer.posX;
                        double y = item.posY - mc.thePlayer.posY;
                        double z = item.posZ - mc.thePlayer.posZ;

                        // get the angle to the item
                        double angle = Math.toDegrees(Math.atan2(z, x)) - 90;

                        // set the player's yaw and pitch
                        mc.thePlayer.rotationYaw = (float) angle;
                        mc.thePlayer.rotationPitch = (float) -Math.toDegrees(Math.atan2(y, distance));

                        if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround)
                            mc.thePlayer.jump();
                        // set the player's velocity
                        mc.thePlayer.motionX = Math.cos(Math.toRadians(angle + 90)) * 0.2;
                        mc.thePlayer.motionZ = Math.sin(Math.toRadians(angle + 90)) * 0.2;
                    }
                }
            }

    }

}
