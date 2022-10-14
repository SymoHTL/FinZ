package com.symo.finz.modules.impl.player;

import com.symo.finz.FinZ;
import com.symo.finz.modules.Module;

public class FastPlace extends Module {

    public FastPlace() {
        super("FastPlace", "FinZ - Player");
    }

    public void onUpdate() {
        // TODO: Implement this method :)
        //mc.rightClickDelayTimer = 0;
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.FastPlaceEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.FastPlaceEnabled = enabled;
    }

    @Override
    public void onDisable() {
        //mc.rightClickDelayTimer = 4;
        super.onDisable();
    }
}
