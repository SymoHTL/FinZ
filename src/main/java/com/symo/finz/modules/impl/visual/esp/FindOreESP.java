package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.modules.Module;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class FindOreESP extends Module {

    public FindOreESP() {
        super("FineOreESP", "visual");
    }

    public void onRender(TickEvent.RenderTickEvent event) {
        //for (BlockPos block : FindOres.foundBlockPos) {
        //    BlockESPUtil.drawESP(block);
        //}
    }

}
