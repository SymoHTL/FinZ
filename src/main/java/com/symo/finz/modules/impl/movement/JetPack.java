package com.symo.finz.modules.impl.movement;

import com.symo.finz.modules.Module;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

public class JetPack extends Module {
    public JetPack() {
        super("JetPack", Keyboard.KEY_J, "FinZ - Movement");
        setKey(Keyboard.KEY_K);
    }

    public void onUpdate() {
        try {
            Vec3 velocity = new Vec3(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
            if (velocity.yCoord >= 0.5)
                return;
            // only do this when the player presses space
            if (mc.gameSettings.keyBindJump.isKeyDown())
                // he do be flying
                mc.thePlayer.setVelocity(velocity.xCoord, 0.5, velocity.zCoord);
        } catch (Exception e) {
            e.printStackTrace();
            this.disable("Error");
        }

    }


}
