package com.symo.finz.modules.impl.movement;

import com.symo.finz.modules.Module;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

public class Spider extends Module {
    public Spider() {
        super("Spider", Keyboard.KEY_P, "FinZ - Movement");
    }


    public void onUpdate() {
            if (!mc.thePlayer.isCollidedHorizontally) return;
            Vec3 velocity = new Vec3(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
            if (velocity.yCoord >= 0.2)
                return;

            mc.thePlayer.setVelocity(velocity.xCoord, 0.2, velocity.zCoord);
    }



}
