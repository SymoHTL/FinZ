package com.symo.finz.modules.impl.movement;

import com.sun.javafx.geom.Vec3d;
import com.symo.finz.modules.Module;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Spider extends Module {
    public Spider() {
        super("Spider", Keyboard.KEY_P, "movement");
    }


    public void onUpdate(TickEvent.ClientTickEvent event)    {
        if(!mc.thePlayer.isCollidedHorizontally) return;
        Vec3d velocity = new Vec3d(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
        if(velocity.y >= 0.2)
            return;

        mc.thePlayer.setVelocity(velocity.x, 0.2, velocity.z);
    }
}
