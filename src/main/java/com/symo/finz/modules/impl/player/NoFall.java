package com.symo.finz.modules.impl.player;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_O, "FinZ - Movement");
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.NoFallEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.NoFallEnabled = enabled;
    }

    public void onUpdate() {
            if (mc.thePlayer.fallDistance > 2.5f) {
                mc.thePlayer.setVelocity(mc.thePlayer.motionX, -0.46f, mc.thePlayer.motionZ);
                silentSendPacket(new C03PacketPlayer(true));
                mc.thePlayer.fallDistance = 0;
            }

    }
}
