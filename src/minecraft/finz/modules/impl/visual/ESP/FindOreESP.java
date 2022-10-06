package finz.modules.impl.visual.ESP;

import finz.command.impl.FindOres;
import finz.events.impl.EventRender;
import finz.modules.Category;
import finz.modules.Module;
import finz.util.esp.BlockESPUtil;
import net.minecraft.util.BlockPos;

public class FindOreESP extends Module {

    public FindOreESP() {
        super("FineOreESP", "Highlights ores", Category.VISUAL);
    }

    public void onRender(EventRender eventRender) {
        for (BlockPos block : FindOres.foundBlockPos) {
            BlockESPUtil.drawESP(block);
        }
    }

}
