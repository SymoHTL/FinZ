package com.symo.finz.modules.impl.combat;


import com.symo.finz.modules.Module;
import com.symo.finz.utils.Timer;
import org.lwjgl.input.Keyboard;

public class Critical extends Module {
    public Timer timer = new Timer();

    public Critical() {
        super("Critical", Keyboard.KEY_C, "FinZ - Combat");
    }

    // TODO this is shit
    public void onUpdate() {
        if (mc.thePlayer.isDead)
            return;

        if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically)
            if (timer.hasTimeElapsed(1000 / 10, true)) {
                mc.thePlayer.motionY = 0.1;
                mc.thePlayer.onGround = false;
            }

    }
}
