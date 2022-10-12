package com.symo.finz.modules.impl.movement;

import com.symo.finz.modules.Module;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {
    public Fly() {
        super("Fly", Keyboard.KEY_F, "FinZ - Movement");
    }

    public void onEnable() {
        try {
            mc.thePlayer.capabilities.allowFlying = true;
            super.onEnable();
        } catch (Exception e) {
            e.printStackTrace();
            this.disable("Error");
        }

    }

    public void onDisable() {
        try {
            if (!mc.thePlayer.capabilities.isCreativeMode)
                mc.thePlayer.capabilities.allowFlying = false;
            super.onDisable();
        } catch (Exception e) {
            e.printStackTrace();
            this.disable("Error");
        }
    }
}
