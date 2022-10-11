package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.EntityESPUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AnimalESP extends Module {

    public AnimalESP() {
        super("AnimalESP", Keyboard.KEY_NUMPAD1, "visual");
    }

    public void onRender(TickEvent.RenderTickEvent event) {
        for (Entity e : mc.theWorld.getLoadedEntityList())
            if (e instanceof EntityAnimal)
                EntityESPUtil.drawESP((EntityAnimal)e);
    }

}
