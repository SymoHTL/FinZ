package com.symo.finz.modules.impl.movement;

import com.sun.javafx.geom.Vec3d;
import com.symo.finz.modules.Module;
import org.lwjgl.input.Keyboard;

public class JetPack extends Module {
    public JetPack() {
        super("JetPack", Keyboard.KEY_J, "FinZ - Movement");
        setKey(Keyboard.KEY_K);
    }

    public void onUpdate() {
        Vec3d velocity = new Vec3d(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
        if (velocity.y >= 0.5)
            return;
        // only do this when the player presses space
        if (mc.gameSettings.keyBindJump.isKeyDown())
            // he do be flying
            mc.thePlayer.setVelocity(velocity.x, 0.5, velocity.z);
    }


}
