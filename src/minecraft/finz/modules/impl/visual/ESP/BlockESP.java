package finz.modules.impl.visual.ESP;

import finz.events.impl.EventRender;
import finz.modules.Category;
import finz.modules.Module;
import finz.modules.impl.player.AutoMine;
import finz.util.Timer;
import finz.util.esp.BlockESPUtil;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class BlockESP extends Module {
    Timer timer = new Timer();
    List<BlockPos> blocks;

    public BlockESP() {
        super("BlockESP", "Highlights blocks", Keyboard.KEY_NUMPAD1, Category.VISUAL);
    }

    public void onRender(EventRender eventRender) {
        if (this.isEnabled()) {
            for (BlockPos block : blocks) {
                BlockESPUtil.drawESP(block);
            }
            if (timer.hasTimeElapsed(500, true)) {
                blocks = AutoMine.blocks;
            }
        }
    }

    @Override
    public void onEnable() {
        blocks = AutoMine.blocks;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        blocks = null;
        super.onDisable();
    }
}
