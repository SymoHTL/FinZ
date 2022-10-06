package aids.modules.impl.misc;

import aids.events.impl.EventPickBlock;
import aids.modules.Category;
import aids.modules.Module;
import aids.modules.impl.player.AutoMine;
import net.minecraft.util.BlockPos;

public class PickBlock extends Module {
    public PickBlock() {
        super("PickBlock", "Pick blocks to break", Category.MISC);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void onPickBlock(EventPickBlock eventPickBlock) {
        BlockPos blockPos = eventPickBlock.getBlockPos();
        if (blockPos != null) {
            if (AutoMine.blocks.contains(blockPos)) {
                AutoMine.blocks.remove(blockPos);
            } else {
                AutoMine.blocks.add(blockPos);
            }
        }
    }
}
