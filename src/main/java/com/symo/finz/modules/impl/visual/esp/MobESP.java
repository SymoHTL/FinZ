package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.EntityESPUtil;
import net.minecraft.entity.monster.EntityMob;
import org.lwjgl.input.Keyboard;

public class MobESP extends Module {
    public MobESP() {
        super("MobESP", Keyboard.KEY_NUMPAD3, "FinZ - Visual");
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.MobESPEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.MobESPEnabled = enabled;
    }

    public void onRender() {
            mc.theWorld.getLoadedEntityList().stream().filter(e -> e instanceof EntityMob).map(e -> (EntityMob) e).forEach(EntityESPUtil::drawESP);
    }


}
