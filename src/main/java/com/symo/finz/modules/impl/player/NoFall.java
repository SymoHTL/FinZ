package com.symo.finz.modules.impl.player;

import com.google.common.eventbus.Subscribe;
import com.symo.finz.modules.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_O,"movement");
    }

    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.thePlayer.fallDistance > 2.5f) {
            mc.thePlayer.setVelocity(mc.thePlayer.motionX, -0.46f, mc.thePlayer.motionZ);
            silentSendPacket(new C03PacketPlayer(true));
            mc.thePlayer.fallDistance = 0;
        }
    }
}
