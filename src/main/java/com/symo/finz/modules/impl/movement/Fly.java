package com.symo.finz.modules.impl.movement;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import org.lwjgl.input.Keyboard;

public class Fly extends Module {
    public Fly() {
        super("Fly", Keyboard.KEY_F, "FinZ - Movement");
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.FlyEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.FlyEnabled = enabled;
    }

    public void onEnable() {
            mc.thePlayer.capabilities.allowFlying = true;
            super.onEnable();
    }

    public void onDisable() {
            if (!mc.thePlayer.capabilities.isCreativeMode)
                mc.thePlayer.capabilities.allowFlying = false;
            super.onDisable();
    }
}
