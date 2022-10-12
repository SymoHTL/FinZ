package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.EntityESPUtil;
import net.minecraft.entity.monster.EntityMob;
import org.lwjgl.input.Keyboard;

public class MobESP extends Module {
    public MobESP() {
        super("MobESP", Keyboard.KEY_NUMPAD3, "FinZ - Visual");
    }

    public void onRender() {
        mc.theWorld.getLoadedEntityList().stream().filter(e -> e instanceof EntityMob).map(e -> (EntityMob) e).forEach(EntityESPUtil::drawESP);
    }


}
