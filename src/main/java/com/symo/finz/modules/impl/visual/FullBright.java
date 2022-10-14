package com.symo.finz.modules.impl.visual;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;

public class FullBright extends Module {

    private float oldGamma;

    public FullBright() {
        super("FullBright", "FinZ - Visual");
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.FullBrightEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.FullBrightEnabled = enabled;
    }

    public void onEnable() {
            oldGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = 100;
            super.onEnable();

    }

    public void onDisable() {
            mc.gameSettings.gammaSetting = oldGamma;
            super.onEnable();
    }
}
