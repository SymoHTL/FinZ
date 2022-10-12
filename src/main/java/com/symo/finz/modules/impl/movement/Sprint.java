package com.symo.finz.modules.impl.movement;

import com.symo.finz.modules.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "FinZ - Movement");
    }

    public void onUpdate() {
            if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isUsingItem())
                mc.thePlayer.setSprinting(true);

    }

    @Override
    public void onDisable() {
            mc.thePlayer.setSprinting(false);
            super.onDisable();
    }
}
