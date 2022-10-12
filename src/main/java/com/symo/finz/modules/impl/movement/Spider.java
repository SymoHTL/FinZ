package com.symo.finz.modules.impl.movement;

import com.sun.javafx.geom.Vec3d;
import com.symo.finz.modules.Module;
import org.lwjgl.input.Keyboard;

public class Spider extends Module {
    public Spider() {
        super("Spider", Keyboard.KEY_P, "FinZ - Movement");
    }


    public void onUpdate() {
        if (!mc.thePlayer.isCollidedHorizontally) return;
        Vec3d velocity = new Vec3d(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
        if (velocity.y >= 0.2)
            return;

        mc.thePlayer.setVelocity(velocity.x, 0.2, velocity.z);
    }
}
