package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.EntityESPUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class MobESP extends Module {
    public MobESP() {
        super("MobESP", Keyboard.KEY_NUMPAD3,"visual");
    }

    public void onRender(TickEvent.RenderTickEvent event) {
        for (Entity e : mc.theWorld.getLoadedEntityList())
            if (e instanceof EntityMob)
                EntityESPUtil.drawESP((EntityMob)e);
    }


}
