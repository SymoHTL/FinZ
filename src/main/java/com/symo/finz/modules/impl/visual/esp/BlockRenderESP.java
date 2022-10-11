package com.symo.finz.modules.impl.visual.esp;

import com.symo.finz.modules.Module;
import com.symo.finz.utils.esp.BlockESPUtil;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class BlockRenderESP extends Module {

    public static List<BlockPos> blockPosList = new ArrayList<>();

    public BlockRenderESP() {
        super("BlockRenderESP", Keyboard.KEY_NUMPAD4, "visual");
    }


    public void onRender(TickEvent.RenderTickEvent event) {
        for (BlockPos block : blockPosList)
            BlockESPUtil.drawESP(block);
    }

}
