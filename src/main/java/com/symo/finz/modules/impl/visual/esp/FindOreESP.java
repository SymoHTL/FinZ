package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.commands.impl.FindOres;
import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.BlockESPUtil;

public class FindOreESP extends Module {

    public FindOreESP() {
        super("FindOreESP", "FinZ - Visual");
    }

    public void onRender() {
            FindOres.foundBlockPos.forEach(BlockESPUtil::drawESP);
    }

}
