package com.symo.finz.modules.impl.player;

import com.symo.finz.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_O, "FinZ - Movement");
    }

    public void onUpdate() {
        try {
            if (mc.thePlayer.fallDistance > 2.5f) {
                mc.thePlayer.setVelocity(mc.thePlayer.motionX, -0.46f, mc.thePlayer.motionZ);
                silentSendPacket(new C03PacketPlayer(true));
                mc.thePlayer.fallDistance = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.disable("Error");
        }

    }
}
