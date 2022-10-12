package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.EntityESPUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

public class PlayerESP extends Module {

    public PlayerESP() {
        super("PlayerESP", Keyboard.KEY_NUMPAD0, "FinZ - Visual");
    }

    public void onRender() {
            for (Entity e : mc.theWorld.getLoadedEntityList())
                if (e instanceof EntityPlayer && e != mc.thePlayer)
                    EntityESPUtil.drawESP((EntityPlayer) e);

    }
}
