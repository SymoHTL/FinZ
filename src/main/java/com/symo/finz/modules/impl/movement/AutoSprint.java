package com.symo.finz.modules.impl.movement;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", "FinZ - Movement");
    }

    public void onUpdate() {
        if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isUsingItem())
            mc.thePlayer.setSprinting(true);

    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.AutoSprintEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.AutoSprintEnabled = enabled;
    }

    @Override
    public void onDisable() {
        mc.thePlayer.setSprinting(false);
        super.onDisable();
    }
}
