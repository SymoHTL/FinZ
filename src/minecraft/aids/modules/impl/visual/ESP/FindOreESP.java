package aids.modules.impl.visual.ESP;

import aids.command.impl.FindOres;
import aids.events.impl.EventRender;
import aids.modules.Category;
import aids.modules.Module;
import aids.util.esp.BlockESPUtil;
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
