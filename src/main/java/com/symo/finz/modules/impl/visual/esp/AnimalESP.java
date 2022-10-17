package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.EntityESPUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityAnimal;
import org.lwjgl.input.Keyboard;

public class AnimalESP extends Module {

    public AnimalESP() {
        super("AnimalESP", Keyboard.KEY_NUMPAD1, "FinZ - Visual");
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.AnimalESPEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.AnimalESPEnabled = enabled;
    }

    public void onRender() {
        for (Entity e : mc.theWorld.getLoadedEntityList())
            if (e instanceof EntityAnimal)
                EntityESPUtil.drawESP((EntityAnimal) e);
    }

}
