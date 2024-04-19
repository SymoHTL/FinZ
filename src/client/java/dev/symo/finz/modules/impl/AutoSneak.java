package dev.symo.finz.modules.impl;

import dev.symo.finz.mixininterfaces.IKeyBind;
import dev.symo.finz.modules.AModule;
import dev.symo.finz.util.Category;

public class AutoSneak extends AModule {
    private boolean sneaking = false;

    public AutoSneak() {
        super("Auto Sneak", Category.MOVEMENT);
        registerKeyBind("key.keyboard.v");
    }

    public void sneak(boolean clipping) {
        if (!isEnabled() || !(mc.player != null && mc.player.isOnGround())) {
            if (sneaking) setSneaking(false);
            return;
        }

        if (mc.world != null && mc.world.isSpaceEmpty(mc.player, mc.player.getBoundingBox().stretch(0, -mc.player.getStepHeight(), 0)))
            clipping = true;

        setSneaking(clipping);
    }

    private void setSneaking(boolean sneak) {
        if (sneak) mc.options.sneakKey.setPressed(true);
        else ((IKeyBind) mc.options.sneakKey).resetPressedState();

        sneaking = sneak;
    }
}
