package com.symo.finz.modules.impl.movement;

import com.symo.finz.modules.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "FinZ - Movement");
    }

    public void onUpdate() {
        try {
            if (mc.thePlayer.moveForward > 0 && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isUsingItem())
                mc.thePlayer.setSprinting(true);
        } catch (Exception e) {
            e.printStackTrace();
            this.disable("Error");
        }

    }

    @Override
    public void onDisable() {
        try {
            mc.thePlayer.setSprinting(false);
            super.onDisable();
        } catch (Exception e) {
            e.printStackTrace();
            this.disable("Error");
        }

    }
}
