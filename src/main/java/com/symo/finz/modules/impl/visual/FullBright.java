package com.symo.finz.modules.impl.visual;

import com.symo.finz.modules.Module;

public class FullBright extends Module {

    private float oldGamma;

    public FullBright() {
        super("FullBright", "FinZ - Visual");
    }

    public void onEnable() {
        try {
            oldGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = 100;
            super.onEnable();
        }catch (Exception e){
            e.printStackTrace();
            this.disable("Error");
        }

    }

    public void onDisable() {
        try {
            oldGamma = mc.gameSettings.gammaSetting;
            mc.gameSettings.gammaSetting = 100;
            super.onEnable();
        }catch (Exception e){
            e.printStackTrace();
            this.disable("Error");
        }
    }
}
