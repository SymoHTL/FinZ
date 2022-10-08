package finz.modules.impl.visual.ESP;

import finz.events.impl.EventRender;
import finz.modules.Category;
import finz.modules.Module;
import finz.util.esp.BlockESPUtil;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class BlockRenderESP extends Module {

    public static List<BlockPos> blockPosList = new ArrayList<>();

    public BlockRenderESP() {
        super("BlockRenderESP", "Highlights blocks", Category.VISUAL);
        setEnabled(true);
    }


    public void onRender(EventRender eventRender) {
        for (BlockPos block : blockPosList) {
            BlockESPUtil.drawESP(block);
        }
    }

}
