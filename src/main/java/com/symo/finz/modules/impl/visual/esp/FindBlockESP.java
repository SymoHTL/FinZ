package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.FinZ;
import com.symo.finz.commands.impl.FindBlock;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.BlockESPUtil;

public class FindBlockESP extends Module {

    public FindBlockESP() {
        super("FindBlockESP", "FinZ - Visual");
    }

    @Override
    public boolean isEnabled() {
        return FinZ.configFile.FindBlockESPEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        FinZ.configFile.FindBlockESPEnabled = enabled;
    }

    public void onRender() {
            FindBlock.foundBlockPos.forEach(BlockESPUtil::drawESP);
    }

}
