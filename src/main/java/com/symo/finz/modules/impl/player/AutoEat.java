package com.symo.finz.modules.impl.player;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.extension.PlayerInventoryExtension;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.TimerTask;

public class AutoEat extends Module {

    public AutoEat() {
        super("AutoEat", "player");
    }

    public void onUpdate(TickEvent.ClientTickEvent event) {
        if (mc.thePlayer.getFoodStats().getFoodLevel() == 20)
            return;
        if (mc.thePlayer.isUsingItem())
            return;

        int slot = PlayerInventoryExtension.getFirstLowerFoodSlot(20 - mc.thePlayer.getFoodStats().getFoodLevel());
        if (slot != -1)
            mc.thePlayer.inventory.currentItem = slot;

        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            }
        }, 75);

    }
}
